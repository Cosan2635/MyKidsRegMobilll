import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mykidsreg.R
import com.example.mykidsreg.models.StudentLog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FirstFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentLogAdapter
    private val studentLogList = mutableListOf<StudentLog>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewChildren)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = StudentLogAdapter(studentLogList)
        recyclerView.adapter = adapter

        // Populate the list with real-time data
        populateStudentLogList()

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun populateStudentLogList() {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

        for (i in 0 until 7) {
            val dateTime = LocalDateTime.now().plusDays(i.toLong())
            studentLogList.add(
                StudentLog(
                    id = i,
                    studentId = i,
                    type = 1,
                    date = dateTime
                )
            )
        }
        adapter.notifyDataSetChanged()
    }
}
