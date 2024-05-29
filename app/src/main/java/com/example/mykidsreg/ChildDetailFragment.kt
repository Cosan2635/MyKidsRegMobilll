package com.example.mykidsreg

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mykidsreg.models.StudentLog
import java.time.format.DateTimeFormatter

class ChildDetailFragment : Fragment() {

    private lateinit var textViewDate: TextView
    private lateinit var textViewName: TextView
    private lateinit var textViewId: TextView
    private lateinit var textViewType: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_child_detail, container, false)

        textViewDate = view.findViewById(R.id.textViewDate)
        textViewName = view.findViewById(R.id.textViewName)
        textViewId = view.findViewById(R.id.textViewArrivalTime)
        textViewType = view.findViewById(R.id.textViewCollectionTime)

        val studentLog = arguments?.getSerializable("studentLog") as? StudentLog
        studentLog?.let { displayStudentLogDetails(it) }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayStudentLogDetails(studentLog: StudentLog) {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        textViewDate.text = studentLog.date?.format(dateFormatter)
        textViewName.text = "Student Name: ${studentLog.studentId}" // Placeholder
        textViewId.text = "ID: ${studentLog.id}"
        textViewType.text = "Type: ${studentLog.type}"
    }
}
