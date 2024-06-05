package com.example.mykidsreg.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykidsreg.R
import com.example.mykidsreg.adapters.MyAdapter
import com.example.mykidsreg.databinding.FragmentChildDetailBinding
import com.example.mykidsreg.repository.StudentRepository
import com.example.mykidsreg.services.ApiClient
import com.example.mykidsreg.viewmodels.StudentViewModel
import com.example.mykidsreg.viewmodels.StudentViewModelFactory

class ChildDetailFragment : Fragment() {

    private var _binding: FragmentChildDetailBinding? = null
    private val binding get() = _binding!!

    private val studentViewModel: StudentViewModel by activityViewModels {
        val apiService = ApiClient.apiService
        val studentRepository = StudentRepository(apiService)
        StudentViewModelFactory(studentRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChildDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt("position") ?: -1
        if (position == -1) {
            findNavController().navigateUp()
            return
        }

        binding.recyclerViewDetails.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MyAdapter(emptyList()) { student ->
            // Do something when item in RecyclerView is clicked
        }
        binding.recyclerViewDetails.adapter = adapter

        binding.Back.setOnClickListener {
            findNavController().navigate(R.id.action_childDetailFragment_to_firstFragment)
        }

        studentViewModel.students.observe(viewLifecycleOwner) { students ->
            val selectedStudent = students.getOrNull(position)
            if (selectedStudent != null) {
                adapter.updateData(listOf(selectedStudent))
            } else {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
