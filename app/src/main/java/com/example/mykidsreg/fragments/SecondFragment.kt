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

import com.example.mykidsreg.databinding.FragmentsecondBinding
import com.example.mykidsreg.repository.StudentRepository
import com.example.mykidsreg.services.ApiClient
import com.example.mykidsreg.viewmodels.StudentViewModel
import com.example.mykidsreg.viewmodels.StudentViewModelFactory

class SecondFragment : Fragment() {

    private var _binding: FragmentsecondBinding? = null
    private val binding get() = _binding!!
    private val TAG = "SecondFragment"

    private val studentViewModel: StudentViewModel by viewModels {
        val apiService = ApiClient.apiService1
        val studentRepository = StudentRepository(apiService)
        StudentViewModelFactory(studentRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentsecondBinding.inflate(inflater, container, false)
        Log.d(TAG, "onCreateView called")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated called")

        try {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            val adapter = MyAdapter(emptyList()) { position ->
                Log.d(TAG, "Item clicked at position: $position")
                try {
                    val action = SecondFragmentDirections.actionSecondFragmentToStudentlog(position)
                    findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.e(TAG, "Navigation error", e)
                    Toast.makeText(requireContext(), "Navigation error", Toast.LENGTH_SHORT).show()
                }
            }
            binding.recyclerView.adapter = adapter

            val sharedPref = requireActivity().getSharedPreferences("myKidsReg", Context.MODE_PRIVATE)
            val userId = sharedPref.getInt("userId", -1)
            Log.d(TAG, "Retrieved userId from SharedPreferences: $userId")

            if (userId != -1) {
                studentViewModel.getStudentsByTeacherId(userId).observe(viewLifecycleOwner, Observer { students ->
                    if (students != null && students.isNotEmpty()) {
                        Log.d(TAG, "Students data received: ${students.size}")
                        adapter.updateData(students)
                    } else {
                        Log.d(TAG, "No students data received")
                        Toast.makeText(
                            requireContext(),
                            "Ingen elevdata modtaget",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } else {
                Log.w(TAG, "Ugyldig bruger ID, henter elever fortsætter ikke")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error setting up RecyclerView", e)
            Toast.makeText(requireContext(), "Error setting up view", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
