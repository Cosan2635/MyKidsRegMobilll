package com.example.mykidsreg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykidsreg.databinding.FragmentFirstBinding
import com.example.mykidsreg.models.Student
import com.example.mykidsreg.models.StudentAdapter

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private val collectors = mutableListOf("Parent 1", "Parent 2", "Relative 1")  // Example collectors

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the RecyclerView
        val students = listOf(
            Student(id = 1, name = "John", lastName = "Doe", birthday = "2005-09-15", departmentId = 1),
            Student(id = 2, name = "Jane", lastName = "Smith", birthday = "2006-07-20", departmentId = 2)
        )

        val adapter = StudentAdapter(students) { position ->
            val clickedStudent = students[position]
            Toast.makeText(requireContext(), "Clicked: ${clickedStudent.name}", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerViewChildren.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewChildren.adapter = adapter

        // Set up the Spinner with collectors
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, collectors)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCollectors.adapter = spinnerAdapter

        // Handle add collector button click
        binding.buttonAddCollector.setOnClickListener {
            // Example: Add a new collector
            collectors.add("New Collector")
            spinnerAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(), "New collector added", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
