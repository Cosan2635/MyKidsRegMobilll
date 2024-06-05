package com.example.mykidsreg.services

import com.example.mykidsreg.models.Department

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DepartmentService {
    @GET("departments")
    fun getDepartmentById(@Query("department_id") department_id: String): Call<List<Department>>
}