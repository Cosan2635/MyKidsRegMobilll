package com.example.mykidsreg.models

import java.io.Serializable
import java.util.Date

enum class Type(val value: Int) {
    Sick(0),
    Arrived(1),
    Gone(2);
}

data class StudentLog(
    var id: Int,
    var studentId: Int,
    var type: Type,
    var date: Date?, // Using java.util.Date here
    var startTime: String,
    var endTime: String,
    var absenceReason: String? = null,
    var isOnLeave: Boolean = false
) : Serializable {
    constructor(studentId: Int, type: Type, date: Date?, startTime: String, endTime: String) : this(
        id = -1,
        studentId = studentId,
        type = type,
        date = date,
        startTime = startTime,
        endTime = endTime
    )
}
