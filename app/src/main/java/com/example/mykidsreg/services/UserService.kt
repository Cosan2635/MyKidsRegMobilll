package com.example.mykidsreg.services

import com.example.mykidsreg.models.Users
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("Users/login")
    fun getUserByUsernameAndPassword(@Query("username") username: String, @Query("password") password: String): Call<List<Users>>
    @GET("users")
    fun GetUser(@Query("user_id") userId :String) : Call<List<Users>>

    @DELETE("users/{id}")
    fun delete(@Path("id") id:Int): Call<Users>
    @PUT("users/{id}")
    fun udateuser(@Path("id") id:Int,@Body user:Users):Call<Users>
    @GET("users/filter")
    fun getUsersByType(@Query("user_type") usertype: Int): Call<List<Users>>
}
