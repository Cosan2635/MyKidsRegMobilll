package com.example.mykidsreg.viewmodels

import Student
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykidsreg.models.ParentsRelation
import com.example.mykidsreg.models.TeacherRelation
import com.example.mykidsreg.repository.StudentRepository
import kotlinx.coroutines.launch

class StudentViewModel(private val studentRepository: StudentRepository) : ViewModel() {

    private val _students = MutableLiveData<List<Student>>()
    val student_id: LiveData<List<Student>> get() = _students

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getParentRelations(userId: Int): LiveData<List<ParentsRelation>> {
        val parentRelations = MutableLiveData<List<ParentsRelation>>()
        viewModelScope.launch {
            try {
                Log.d("StudentViewModel", "Fetching parent relations for userId: $userId")
                val relations = studentRepository.getParentRelations(userId)
                Log.d("StudentViewModel", "Fetched parent relations: $relations")
                parentRelations.value = relations
            } catch (e: Exception) {
                _error.value = "Failed to load parent relations: ${e.message}"
                Log.e("StudentViewModel", "Exception: ${e.message}")
            }
        }
        return parentRelations
    }

    fun getStudentsByParentUserId(userId: Int): LiveData<List<ParentsRelation>> {
        val parentRelations = MutableLiveData<List<ParentsRelation>>()
        viewModelScope.launch {
            try {
                Log.d("StudentViewModel", "Fetching parent relations for parent userId: $userId")

                val relations = studentRepository.getParentRelations(userId)

                parentRelations.value = relations
            } catch (e: Exception) {
                _error.value = "Failed to load parent relations by parent user ID: ${e.message}"
                Log.e("StudentViewModel", "Exception: ${e.message}")
            }
        }
        return parentRelations
    }

    fun getStudentsByIds(student_id: List<Int>): LiveData<List<Student>> {
        val students = MutableLiveData<List<Student>>()
        if (student_id.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    Log.d("StudentViewModel", "Fetching students for ids: $student_id")
                    val studentsList = studentRepository.getStudentsByIds(student_id)
                    Log.d("StudentViewModel", "Fetched students: $studentsList")
                    students.value = studentsList
                } catch (e: Exception) {
                    _error.value = "Failed to load students by IDs: ${e.message}"
                    Log.e("StudentViewModel", "Exception: ${e.message}")
                }
            }
        } else {
            _error.value = "Student IDs list is empty"
            Log.e("StudentViewModel", "Student IDs list is empty")
        }
        return students
    }

    fun getTeacherRelations(userId: Int): LiveData<List<TeacherRelation>> {
        val teacherRelations = MutableLiveData<List<TeacherRelation>>()
        viewModelScope.launch {
            try {
                val relations = studentRepository.getTeacherRelations(userId)
                teacherRelations.value = relations
            } catch (e: Exception) {
                _error.value = "Failed to load teacher relations: ${e.message}"
                Log.e("StudentViewModel", "Exception: ${e.message}")
            }
        }
        return teacherRelations
    }

    fun getStudentsByDepartmentId(department_id: Int): LiveData<List<Student>> {
        val studentsLiveData = MutableLiveData<List<Student>>()
        viewModelScope.launch {
            try {
                val response = studentRepository.getStudentsByDepartmentId(department_id)
                if (response != null) {
                    studentsLiveData.value = response
                } else {
                    _error.value = "Failed to load students by department ID"
                    Log.e("StudentViewModel", "Failed to load students by department ID")
                }
            } catch (e: Exception) {
                _error.value = "Failed to load students by department ID: ${e.message}"
                Log.e("StudentViewModel", "Exception: ${e.message}")
            }
        }
        return studentsLiveData
    }

    fun getStudentsByTeacherId(userId: Int): LiveData<List<Student>> {
        val students = MutableLiveData<List<Student>>()
        viewModelScope.launch {
            try {
                val relations = studentRepository.getTeacherRelations(userId)
                val student_id = relations.map { it.user_id }
                val studentList = studentRepository.getStudentsByIds(student_id)
                students.value = studentList
            } catch (e: Exception) {
                _error.value = "Failed to load students by teacher ID: ${e.message}"
                Log.e("StudentViewModel", "Exception: ${e.message}")
            }
        }
        return students
    }
}
