package com.example.mykidsreg.repository

import com.example.mykidsreg.models.Student
import com.example.mykidsreg.models.StudentLog
import com.example.mykidsreg.services.ApiService
import com.example.mykidsreg.services.MyKidsRegService

class MyKidsRegRepository(private val myKidsRegService: ApiService) {

    suspend fun getChildren(token: String): List<Student> {
        return myKidsRegService.getChildren(token)
    }

    suspend fun updateStudentLog(studentLog: StudentLog) {
        myKidsRegService.updateStudentLog(studentLog)
    }
}
