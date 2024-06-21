import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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
        try {
            holder.bind(students[position], itemClickListener)
        } catch (e: Exception) {
            // Handle exception if needed
        }
    }

    override fun getItemCount(): Int = students.size

    fun updateData(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progressBarTime)

        fun bind(student: Student, clickListener: (Int) -> Unit) {
            nameTextView.text = "${student.name} ${student.last_name}" // Assuming lastName is correct
            itemView.setOnClickListener { clickListener(adapterPosition) }

            // Set progress value (example: 50%)
            progressBar.progress = 50

            // Conditionally change progress bar color based on registration status
            if (student.isRegistered()) {
                // If student is registered, set progress bar color to green
                progressBar.progressDrawable?.setTint(itemView.context.getColor(R.color.green))
            } else {
                // Otherwise, use the default color (blue)
                progressBar.progressDrawable?.setTint(itemView.context.getColor(R.color.blue))
            }
        }
    }


}
