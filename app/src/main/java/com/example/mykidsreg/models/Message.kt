package com.example.mykidsreg.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
data class Message(
    var messageId: Int,
    var date: LocalDate,
    var time: LocalTime,
    var description: String,
    var userId: Int?,
    var institutionId: Int?,
    var user: Users? = null,
    var userNavigation: Users? = null
) : Serializable {
    var createdAt: LocalDateTime = LocalDateTime.now()

    init {
        createdAt = LocalDateTime.now()
        date = LocalDate.now()
        time = LocalTime.now()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(
        description: String,
        userId: Int?,
        institutionId: Int?
    ) : this(
        -1,
        LocalDate.now(),
        LocalTime.now(),
        description,
        userId,
        institutionId
    )
}