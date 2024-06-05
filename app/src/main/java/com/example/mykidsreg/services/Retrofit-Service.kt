package com.example.mykidsreg.services

import Student
import com.example.mykidsreg.models.ParentsRelation
import com.example.mykidsreg.models.TeacherRelation
import com.example.mykidsreg.models.UserTypeAdapter
import com.example.mykidsreg.models.Users
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class LoginRequest(val username: String, val password: String)
data class LoginResponse(val username: String, val name: String, val usertype: Int, val user_id: Int)

interface ApiService {
    @POST("/api/Users/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("Users/{id}")
    fun getUserInfo(@Path("id") id: Int): Call<Users>

    @GET("ParentRelations/user/{userId}")
    fun getParentRelations(@Path("userId") userId: Int): Call<List<ParentsRelation>>
    @GET("TeacherRelation/user/{userId}")
    fun getTeacherRelations(@Path("userId") userId: Int): Call<List<TeacherRelation>>


    @GET("Student")
    fun getStudentsByIds(@Query("id") ids: List<Int>): Call<List<Student>>
}

object ApiClient {
    private const val BASE_URL = "http://172.29.16.1:5191/api/"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(interceptor)
    }.build()

    private val moshi = Moshi.Builder()
        .add(UserTypeAdapter())
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val apiService1: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}
