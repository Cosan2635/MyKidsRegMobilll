package com.example.mykidsreg.models

import java.io.Serializable
import java.time.LocalDateTime

data class StudentLog(
    var id: Int,
    var studentId: Int,
    var type: Int,
    var date: LocalDateTime?,
    var startTime: String,
    var endTime: String,
    var absenceReason: String? = null,
    var isOnLeave: Boolean = false
) : Serializable {
    constructor(studentId: Int, type: Int, date: LocalDateTime?, startTime: String, endTime: String) : this(
        id = -1,
        studentId = studentId,
        type = type,
        date = date,
        startTime = startTime,
        endTime = endTime
    )
}
