package com.example.mykidsreg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mykidsreg.databinding.FragmentFirstBinding
import com.example.mykidsreg.models.Student
import com.example.mykidsreg.models.StudentAdapter

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example list of students
        val students = listOf(
            Student(id = 1, name = "John", lastName = "Doe", birthday = "2005-09-15", departmentId = 1),
            Student(id = 2, name = "Jane", lastName = "Smith", birthday = "2006-07-20", departmentId = 2)
        )

        // Set up the RecyclerView
        val adapter = StudentAdapter(students) { position ->
            // Handle item click
            val clickedStudent = students[position]
            // Do something with clickedStudent, e.g., show a Toast or navigate to a detail view
            Toast.makeText(requireContext(), "Clicked: ${clickedStudent.name}", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerViewChildren.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewChildren.adapter = adapter

        // Handle button click
        binding.buttonChooseCollector.setOnClickListener {
            // Handle button click, e.g., show a dialog or navigate to another fragment
            Toast.makeText(requireContext(), "Vælg afhenter clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
