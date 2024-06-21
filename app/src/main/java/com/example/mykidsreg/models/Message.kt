package com.example.mykidsreg.models

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Message(
    var messageId: Int,
    var date: LocalDate = LocalDate.now(),
    var time: LocalTime = LocalTime.now(),
    var description: String,
    var userId: Int,
    var institutionId: Int,
    val user: Users? = null
) : Serializable {
    var createdAt: LocalDateTime = LocalDateTime.now()

    constructor(
        description: String,
        userId: Int,
        institutionId: Int
    ) : this(
        -1,
        LocalDate.now(),
        LocalTime.now(),
        description,
        userId,
        institutionId
    )
}
