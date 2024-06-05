package com.example.mykidsreg.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykidsreg.adapters.MyAdapter
import com.example.mykidsreg.databinding.FragmentsecondBinding
import com.example.mykidsreg.repository.StudentRepository
import com.example.mykidsreg.services.ApiClient
import com.example.mykidsreg.viewmodels.StudentViewModel
import com.example.mykidsreg.viewmodels.StudentViewModelFactory

class SecondFragment : Fragment() {

    private var _binding: FragmentsecondBinding? = null
    private val binding get() = _binding!!

    private val studentViewModel: StudentViewModel by viewModels {
        val apiService = ApiClient.apiService
        val studentRepository = StudentRepository(apiService)
        StudentViewModelFactory(studentRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentsecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MyAdapter(emptyList()) { position ->
            // Handle item click if needed
        }
        binding.recyclerView.adapter = adapter

        val sharedPref = requireActivity().getSharedPreferences("myKidsReg", Context.MODE_PRIVATE)
        val teacherId = sharedPref.getInt("teacherId", -1)

        if (teacherId != -1) {
            studentViewModel.getStudentsByTeacherId(teacherId).observe(viewLifecycleOwner, Observer { students ->
                adapter.updateData(students)
            })

            studentViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
                if (errorMessage != null) {
                    // Handle error
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
