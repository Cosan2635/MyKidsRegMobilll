package com.example.mykidsreg.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.mykidsreg.R
import com.example.mykidsreg.models.StudentLog
import java.time.format.DateTimeFormatter

class StudentLogAdapter(
    private val studentLogList: List<StudentLog>,
    private val itemClickListener: (StudentLog) -> Unit
) : RecyclerView.Adapter<StudentLogAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_child, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studentLog = studentLogList[position]
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        holder.textViewDate.text = studentLog.date?.format(dateFormatter)
        holder.textViewName.text = "Student ${studentLog.studentId}"
        holder.itemView.setOnClickListener { itemClickListener(studentLog) }
    }

    override fun getItemCount(): Int {
        return studentLogList.size
    }
}
