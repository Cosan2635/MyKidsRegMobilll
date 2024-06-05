package com.example.mykidsreg.adapters

import Student
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mykidsreg.databinding.ItemChildBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MyAdapter(private var students: List<Student>, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<MyAdapter.StudentViewHolder>() {

    class StudentViewHolder(val binding: ItemChildBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.binding.apply {
            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            val date = LocalDate.parse(student.birthday, dateFormatter)

            textViewDate.text = date.dayOfMonth.toString()
            textViewDay.text = date.dayOfWeek.toString().substring(0, 3) // e.g., "Mon"
            textViewName.text = student.name
        }

        holder.itemView.setOnClickListener {
            onItemClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newStudents: List<Student>) {
        this.students = newStudents
        notifyDataSetChanged()
    }
}
