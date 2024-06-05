package com.example.mykidsreg.repository

import Student
import android.util.Log
import com.example.mykidsreg.models.ParentsRelation
import com.example.mykidsreg.models.TeacherRelation
import com.example.mykidsreg.services.ApiClient.apiService1
import com.example.mykidsreg.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentRepository(private val apiService: ApiService) {

    suspend fun getParentRelations(userId: Int): List<ParentsRelation> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getParentRelations(userId).execute()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Failed to fetch parent relations: ${response.message()}")
            }
        }
    }

    suspend fun getStudentsByIds(student_id: List<Int>): List<Student> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("StudentRepository", "Fetching students by IDs: $student_id")
                val response = apiService1.getStudentsByIds(student_id).execute()
                if (response.isSuccessful) {
                    Log.d("StudentRepository", "Successful response: ${response.body()}")
                    response.body() ?: emptyList()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("StudentRepository", "Error response: $errorBody")
                    throw Exception("Failed to fetch students: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("StudentRepository", "Exception: ${e.message}")
                throw e
            }
        }
    }
    suspend fun getTeacherRelations(userId: Int): List<TeacherRelation> {
        return withContext(Dispatchers.IO) {
            val response = apiService.getTeacherRelations(userId).execute()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Failed to fetch teacher relations: ${response.message()}")
            }
        }
    }


}
