package com.example.mykidsreg.services

import com.example.mykidsreg.models.Student
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val success: Boolean, val token: String)

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("students")
    fun getStudents(): Call<List<Student>>
}

object ApiClient {
    private const val BASE_URL = "https://your.api.base.url/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
