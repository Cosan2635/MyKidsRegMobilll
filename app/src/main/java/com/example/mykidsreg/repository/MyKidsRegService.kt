package com.example.mykidsreg.services

import com.example.mykidsreg.models.Student
import com.example.mykidsreg.models.StudentLog
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface MyKidsRegService {
    @GET("students/children")
    suspend fun getChildren(@Header("Authorization") token: String): List<Student>

    @PUT("studentLog/update")
    suspend fun updateStudentLog(@Body studentLog: StudentLog): Void
}
