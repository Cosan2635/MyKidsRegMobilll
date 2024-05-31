package com.example.mykidsreg.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mykidsreg.databinding.ItemChildBinding
import com.example.mykidsreg.models.Student
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MyAdapter(private var students: List<Student>, private val onItemClicked: (Student) -> Unit) : RecyclerView.Adapter<MyAdapter.StudentViewHolder>() {

    class StudentViewHolder(val binding: ItemChildBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.binding.apply {
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val date = LocalDate.parse(student.birthday, dateFormatter)

            textViewDate.text = date.dayOfMonth.toString()
            textViewDay.text = date.dayOfWeek.toString().substring(0, 3) // e.g., "Mon"
            textViewName.text = student.name
            val log = student.studentLogs.firstOrNull()
            if (log != null) {
                // Assuming startTime and endTime are strings in the format "HH:mm"
                val startTime = log.startTime ?: "00:00"
                val endTime = log.endTime ?: "00:00"
                textViewTime.text = "$startTime - $endTime"
                // Calculate progress based on start and end time
                progressBarTime.progress = calculateProgress(startTime, endTime)
            }
            root.setOnClickListener {
                onItemClicked(student)
            }
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }

    fun updateData(newStudents: List<Student>) {
        this.students = newStudents
        notifyDataSetChanged()
    }

    private fun calculateProgress(startTime: String, endTime: String): Int {
        // Implement your logic to calculate progress based on time
        val start = startTime.split(":").map { it.toInt() }
        val end = endTime.split(":").map { it.toInt() }
        val startMinutes = start[0] * 60 + start[1]
        val endMinutes = end[0] * 60 + end[1]
        return (endMinutes - startMinutes) * 100 / (24 * 60) // Assuming a day has 24 hours
    }
}
