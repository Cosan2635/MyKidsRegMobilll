package com.example.mykidsreg.repository

import Student
import android.util.Log
import com.example.mykidsreg.models.ParentsRelation
import com.example.mykidsreg.models.TeacherRelation
import com.example.mykidsreg.services.ApiClient.apiService
import com.example.mykidsreg.services.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class StudentRepository(private val apiService1: ApiService) {

    suspend fun getParentRelations(userId: Int): List<ParentsRelation> {
        return withContext(Dispatchers.IO) {
            val response = apiService1.getParentRelations(userId).execute()
            if (response.isSuccessful) {
                response.body() ?: emptyList()
            } else {
                throw Exception("Failed to fetch parent relations: ${response.message()}")
            }
        }
    }

    suspend fun getStudentsByIds(studentIds: List<Int>): List<Student> {
        val nonNullStudentIds = studentIds.filterNotNull()

        return withContext(Dispatchers.IO) {
            try {
                Log.d("StudentRepository", "Fetching students by IDs: $nonNullStudentIds")
                val response = apiService1.getStudentsByIds(nonNullStudentIds).execute()
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    throw Exception("Failed to fetch students by IDs: ${response.message()}")
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

    suspend fun getTeacherRelationFromApi(userId: Int): TeacherRelation? {
        return try {
            Log.d("StudentRepository", "Fetching teacher relation from API for userId: $userId")
            val response = withContext(Dispatchers.IO) {
                apiService1.getTeacherRelations(userId).execute()
            }
            if (response.isSuccessful) {
                Log.d("StudentRepository", "Successful response received for teacher relation")
                response.body()?.firstOrNull()
            } else {
                Log.e("StudentRepository", "Failed to fetch teacher relation: ${response.message()}")
                null
            }
        } catch (e: Exception) {
            Log.e("StudentRepository", "Error in getTeacherRelationFromApi: ${e.message}")
            null
        }
    }

    suspend fun getStudentsByDepartmentId(departmentId: Int): List<Student> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService1.getStudentsByDepartmentId(departmentId).execute()
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    throw Exception("Failed to fetch students by department ID: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("StudentRepository", "Exception: ${e.message}")
                throw e
            }
        }
    }
}
