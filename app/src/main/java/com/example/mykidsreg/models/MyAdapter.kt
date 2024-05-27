package com.example.mykidsreg.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mykidsreg.R

class StudentAdapter(
    private val items: List<Student>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_child, viewGroup, false)
        return StudentViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: StudentViewHolder, position: Int) {
        viewHolder.bind(items[position])
    }

    class StudentViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val childName: TextView = itemView.findViewById(R.id.childName)
        private val childBirthday: TextView = itemView.findViewById(R.id.childBirthday)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(student: Student) {
            childName.text = student.name
            childBirthday.text = student.birthday
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onItemClicked(position)
            }
        }
    }
}
