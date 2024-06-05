package com.example.mykidsreg.models
import com.google.gson.annotations.SerializedName
data class ParentsRelation(
    @SerializedName("id")
    val id: Int,
    @SerializedName("user_id")
    val user_id: Int,
    @SerializedName("student_id")
    val student_id: Int
)
