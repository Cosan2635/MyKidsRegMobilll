package com.example.mykidsreg.services

import Student
import com.example.mykidsreg.models.ParentsRelation
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface StudentService {
    @GET("students")
    suspend fun getStudents(): List<Student>

    @GET("students")
    suspend fun getStudents(@Query("parameter") parameter: String): List<Student>

    @GET("ParentRelations/user/{userId}")
    suspend fun getParentRelations(@Path("userId") userId: Int): List<ParentsRelation>

    @POST("students/byIds")
    suspend fun getStudentsByIds(@Body id: List<Int>): List<Student>
}
