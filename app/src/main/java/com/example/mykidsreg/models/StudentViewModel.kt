package com.example.mykidsreg.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykidsreg.models.Student
import com.example.mykidsreg.models.StudentLog
import com.example.mykidsreg.repository.MyKidsRegRepository
import kotlinx.coroutines.launch

class StudentViewModel(private val repository: MyKidsRegRepository) : ViewModel() {

    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> get() = _students

    fun setStudents(studentList: List<Student>) {
        _students.value = studentList
    }

    fun loadChildren(token: String) {
        viewModelScope.launch {
            val students = repository.getChildren(token)
            _students.value = students
        }
    }

    fun updateStudentLog(studentLog: StudentLog) {
        viewModelScope.launch {
            repository.updateStudentLog(studentLog)
        }
    }
}
