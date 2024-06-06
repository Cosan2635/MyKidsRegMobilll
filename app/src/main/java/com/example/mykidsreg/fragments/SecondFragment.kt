package com.example.mykidsreg.fragments

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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykidsreg.adapters.MyAdapter
import com.example.mykidsreg.databinding.FragmentsecondBinding
import com.example.mykidsreg.repository.StudentRepository
import com.example.mykidsreg.services.ApiClient
import com.example.mykidsreg.viewmodels.StudentViewModel
import com.example.mykidsreg.viewmodels.StudentViewModelFactory
import kotlinx.coroutines.launch

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

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MyAdapter(emptyList()) { position ->
            Log.d(TAG, "Item clicked at position: $position")
        }
        binding.recyclerView.adapter = adapter

        val sharedPref = requireActivity().getSharedPreferences("myKidsReg", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("userId", -1)
        Log.d(TAG, "Retrieved userId from SharedPreferences: $userId")

        if (userId != -1) {
            fetchDepartmentIdForTeacher(userId) { departmentId ->
                if (departmentId != null) {
                    Log.d(TAG, "Valid departmentId found: $departmentId, fetching students")
                    studentViewModel.getStudentsByDepartmentId(departmentId).observe(viewLifecycleOwner, Observer { students ->
                        if (students != null && students.isNotEmpty()) {
                            adapter.updateData(students)
                        } else {
                            Log.d(TAG, "No students data received")
                            Toast.makeText(requireContext(), "No students data received", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Log.w(TAG, "Invalid departmentId, fetching students will not proceed")
                }
            }
        } else {
            Log.w(TAG, "Invalid userId, fetching students will not proceed")
        }

        studentViewModel.error.observe(viewLifecycleOwner, Observer { errorMessage ->
            if (errorMessage != null) {
                Log.e(TAG, "Error fetching students: $errorMessage")
                Toast.makeText(requireContext(), "Error fetching students", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchDepartmentIdForTeacher(userId: Int, callback: (Int?) -> Unit) {
        Log.d(TAG, "Fetching departmentId for teacherId: $userId")
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val teacherRelations = studentViewModel.getStudentsByTeacherId(userId).value
                if (!teacherRelations.isNullOrEmpty()) {
                    val departmentId = teacherRelations.first().department_id
                    Log.d(TAG, "Department ID fetched: $departmentId")
                    callback(departmentId)
                } else {
                    Log.e(TAG, "No teacher relations found for user ID: $userId")
                    callback(null)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching departmentId: ${e.message}")
                callback(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
