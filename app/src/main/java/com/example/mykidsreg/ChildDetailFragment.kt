import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mykidsreg.R
import com.example.mykidsreg.data.data
import com.example.mykidsreg.models.StudentLog
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FirstFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentLogAdapter.ChildrenAdapter
    private val studentLogList = mutableListOf<StudentLog>()
    private lateinit var spinnerCollectors: Spinner
    private val collectorsList = listOf(
        data.Collector(1, "John Doe"),
        data.Collector(2, "Jane Smith"),
        data.Collector(3, "Emily Johnson")
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewChildren)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = StudentLogAdapter.ChildrenAdapter(studentLogList) { studentLog ->
            openChildDetailFragment(studentLog)
        }
        recyclerView.adapter = adapter

        spinnerCollectors = view.findViewById(R.id.spinnerCollectors)
        setupSpinner()

        // Populate the list with real-time data
        populateStudentLogList()

        return view
    }

    private fun setupSpinner() {
        val collectorNames = collectorsList.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, collectorNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCollectors.adapter = adapter

        spinnerCollectors.setOnItemSelectedListener { _, _, position, _ ->
            val selectedCollector = collectorsList[position]
            Toast.makeText(requireContext(), "Selected: ${selectedCollector.name}", Toast.LENGTH_SHORT).show()
            // Handle the selection
        }
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

    private fun openChildDetailFragment(studentLog: StudentLog) {
        val fragment = ChildDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("studentLog", studentLog)
            }
        }
        parentFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }
    }
}
