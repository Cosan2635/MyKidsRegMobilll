import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mykidsreg.R

class MyAdapter(
    private var students: List<Student>,
    private val itemClickListener: (Int) -> Unit
) : RecyclerView.Adapter<MyAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_child, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(students[position], itemClickListener)
    }

    override fun getItemCount(): Int = students.size

    fun updateData(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)

        fun bind(student: Student, clickListener: (Int) -> Unit) {
            nameTextView.text = "${student.name} ${student.last_name}"
            itemView.setOnClickListener { clickListener(adapterPosition) }
        }
    }
}
