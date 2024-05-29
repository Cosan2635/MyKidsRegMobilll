package com.example.mykidsreg.models

import java.io.Serializable
import java.time.LocalDateTime
data class StudentLog(
    var id: Int,
    var studentId: Int,
    var type: Int,
    var date: LocalDateTime?
) : Serializable {
    constructor(studentId: Int, type: Int, date: LocalDateTime?) : this(
        id = -1,
        studentId = studentId,
        type = type,
        date = date
    )
}
