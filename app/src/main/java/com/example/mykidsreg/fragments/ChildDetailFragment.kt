package com.example.mykidsreg.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykidsreg.R
import com.example.mykidsreg.adapters.MyAdapter
import com.example.mykidsreg.databinding.FragmentChildDetailBinding
import com.example.mykidsreg.viewmodels.StudentViewModel
import com.example.mykidsreg.viewmodels.StudentViewModelFactory
import com.example.mykidsreg.repository.MyKidsRegRepository
import com.example.mykidsreg.services.ApiClient

class ChildDetailFragment : Fragment() {

    private var _binding: FragmentChildDetailBinding? = null
    private val binding get() = _binding!!
    private val studentViewModel: StudentViewModel by viewModels {
        StudentViewModelFactory(MyKidsRegRepository(ApiClient.apiService))
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

        binding.recyclerViewDetails.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MyAdapter(emptyList()) { student ->
            val action = ChildDetailFragmentDirections.actionChildDetailFragmentToFirstFragment()
            findNavController().navigate(action)
        }
        binding.recyclerViewDetails.adapter = adapter

        // Antag at token er gemt i en delt præference eller på en anden måde hentet
        val token = "Bearer " + getTokenFromPreferences()

        studentViewModel.students.observe(viewLifecycleOwner, { students ->
            adapter.updateData(students)
        })

        studentViewModel.loadChildren(token)
    }

    private fun getTokenFromPreferences(): String {
        // Implementer logikken for at hente token fra delt præference eller en anden kilde
        return "your_token_here"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
