package com.example.mykidsreg.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mykidsreg.R
import com.example.mykidsreg.models.StudentLog
import com.example.mykidsreg.models.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class StudentLogFragment : Fragment() {

    private lateinit var startTimeEditText: EditText
    private lateinit var endTimeEditText: EditText
    private lateinit var absenceReasonEditText: EditText
    private lateinit var isOnLeaveCheckBox: CheckBox
    private lateinit var registerButton: Button
    private lateinit var messageButton: Button
    private lateinit var arrivalCheckbox: CheckBox
    private lateinit var departureCheckbox: CheckBox

    private var studentId: Int = -1 // Default studentId

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_studentlog, container, false)

        startTimeEditText = view.findViewById(R.id.start_time)
        endTimeEditText = view.findViewById(R.id.end_time)
        absenceReasonEditText = view.findViewById(R.id.absence_reason)
        isOnLeaveCheckBox = view.findViewById(R.id.is_on_leave)
        registerButton = view.findViewById(R.id.register_button)
        messageButton = view.findViewById(R.id.message_button)
        arrivalCheckbox = view.findViewById(R.id.arrival_checkbox)
        departureCheckbox = view.findViewById(R.id.departure_checkbox)

        // Retrieve studentId from arguments
        arguments?.let {
            studentId = it.getInt("studentId", -1)
        }

        registerButton.setOnClickListener {
            registerStudentLog()
        }

        messageButton.setOnClickListener {
            sendMessageToParents()
        }

        arrivalCheckbox.setOnClickListener {
            onArrivalCheckboxClicked()
        }

        departureCheckbox.setOnClickListener {
            onDepartureCheckboxClicked()
        }

        return view
    }

    private fun registerStudentLog() {
        val startTime = startTimeEditText.text.toString()
        val endTime = endTimeEditText.text.toString()
        val absenceReason = absenceReasonEditText.text.toString()
        val isOnLeave = isOnLeaveCheckBox.isChecked

        // Assuming studentId is valid and retrieved correctly
        val studentLog = StudentLog(
            id = -1, // Assuming this will be set by the database
            studentId = studentId, // Replace with actual student ID
            type = Type.Arrived, // Replace with actual type enum value
            date = Date(), // Example for date, replace with actual date logic
            startTime = startTime,
            endTime = endTime,
            absenceReason = if (isOnLeave) absenceReason else null,
            isOnLeave = isOnLeave
        )

        // Here you would save the studentLog to your database or repository
        // For example, studentViewModel.saveStudentLog(studentLog)

        // Navigate back to SecondFragment
        findNavController().navigate(R.id.action_studentlog_to_secondFragment)
    }

    private fun sendMessageToParents() {
        findNavController().navigate(R.id.action_studentlog_to_fragment_message)
    }

    private fun onArrivalCheckboxClicked() {
        if (arrivalCheckbox.isChecked) {
            val currentTime = getCurrentTime()
            startTimeEditText.setText(currentTime)
        } else {
            startTimeEditText.setText("")
        }
    }

    private fun onDepartureCheckboxClicked() {
        if (departureCheckbox.isChecked) {
            val currentTime = getCurrentTime()
            endTimeEditText.setText(currentTime)
        } else {
            endTimeEditText.setText("")
        }
    }

    private fun getCurrentTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return current.format(formatter)
    }
}
