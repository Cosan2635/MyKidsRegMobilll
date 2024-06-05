package com.example.mykidsreg.services

import Student
import com.example.mykidsreg.models.StudentLog
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface MyKidsRegService {
    @GET("students")
    suspend fun getChildren(@Header("Authorization") token: String): List<Student>

    @PUT("studentLog/update")
    suspend fun updateStudentLog(@Body studentLog: StudentLog): Void
}
