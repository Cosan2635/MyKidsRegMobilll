package com.example.mykidsreg

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.mykidsreg.models.StudentLog
import java.time.format.DateTimeFormatter

class ChildDetailFragment : Fragment() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_child_detail, container, false)

        val studentLog = arguments?.getSerializable("studentLog") as StudentLog

        val textViewDate: TextView = view.findViewById(R.id.textViewDate)
        val textViewName: TextView = view.findViewById(R.id.textViewName)
        val textViewArrivalTime: TextView = view.findViewById(R.id.textViewArrivalTime)
        val textViewCollectionTime: TextView = view.findViewById(R.id.textViewCollectionTime)
        val textViewReasonForAbsence: TextView = view.findViewById(R.id.textViewReasonForAbsence)
        val switchOnLeave: Switch = view.findViewById(R.id.switchOnLeave)

        textViewDate.text = "Date: ${studentLog.date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}"
        textViewName.text = "Name: ${studentLog.studentId}" // Assuming studentId is used as name
        textViewArrivalTime.text = "Arrival Time: ${studentLog.date?.format(DateTimeFormatter.ofPattern("HH:mm"))}"
        textViewCollectionTime.text = "Collection Time: ${studentLog.date?.plusHours(8)?.format(DateTimeFormatter.ofPattern("HH:mm"))}" // Assuming 8 hours stay
        textViewReasonForAbsence.text = "Reason for Absence: ${studentLog.type}" // Modify according to actual attribute
        switchOnLeave.isChecked = false // Set initial state, modify based on actual data

        return view
    }
}
