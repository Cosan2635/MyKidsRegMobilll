package com.example.mykidsreg.fragments

import MyAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykidsreg.databinding.FragmentFirstBinding
import com.example.mykidsreg.repository.StudentRepository
import com.example.mykidsreg.services.ApiClient
import com.example.mykidsreg.viewmodels.StudentViewModel
import com.example.mykidsreg.viewmodels.StudentViewModelFactory

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val studentViewModel: StudentViewModel by viewModels {
        val apiService = ApiClient.apiService1
        val studentRepository = StudentRepository(apiService)
        StudentViewModelFactory(studentRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewChildren.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MyAdapter(emptyList()) { position ->
            val action = FirstFragmentDirections.actionFirstFragmentToChildDetailFragment(position)
            findNavController().navigate(action)
        }
        binding.recyclerViewChildren.adapter = adapter

        val sharedPref = requireActivity().getSharedPreferences("myKidsReg", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("userId", -1)
        Log.d("FirstFragment", "Parent User ID from SharedPreferences: $userId")

        if (userId != -1) {
            studentViewModel.getParentRelations(userId).observe(viewLifecycleOwner, Observer { parentRelations ->
                if (parentRelations != null && parentRelations.isNotEmpty()) {
                    val studentIds = parentRelations.map { it.student_id }
                    Log.d("FirstFragment", "Student IDs for parent userId $userId: $studentIds")
                    studentViewModel.getStudentsByIds(studentIds).observe(viewLifecycleOwner, Observer { students ->
                        Log.d("FirstFragment", "Fetched students for parent userId $userId: $students")
                        val filteredStudents = students.filter { student ->
                            parentRelations.any { it.student_id == student.id }
                        }
                        adapter.updateData(filteredStudents)
                    })
                } else {
                    Log.d("FirstFragment", "No parent relations found for parent userId: $userId")
                    Toast.makeText(requireContext(), "No parent relations found", Toast.LENGTH_SHORT).show()
                }
            })
            studentViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
                if (errorMessage != null) {
                    Log.e("FirstFragment", "Error: $errorMessage")
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Log.e("FirstFragment", "Parent User ID not found in SharedPreferences")
            Toast.makeText(requireContext(), "Parent User ID not found", Toast.LENGTH_SHORT).show()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}