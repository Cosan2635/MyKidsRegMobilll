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
import retrofit2.HttpException

class StudentViewModel(private val studentRepository: StudentRepository) : ViewModel() {

    private val _students = MutableLiveData<List<Student>>()
    val students: LiveData<List<Student>> get() = _students

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
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    _error.value = "Parent relations not found"
                } else {
                    _error.value = "Failed to load parent relations"
                }
                Log.e("StudentViewModel", "HttpException: ${e.message()}")
            } catch (e: Exception) {
                _error.value = "Failed to load parent relations"
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

                // Fetch parent relations for the given user ID
                val relations = studentRepository.getParentRelations(userId)

                // Return the list of parent relations
                parentRelations.value = relations
            } catch (e: Exception) {
                _error.value = "Failed to load parent relations by parent user ID"
                Log.e("StudentViewModel", "Exception: ${e.message}")
            }
        }
        return parentRelations
    }



    fun getStudentsByIds(student_id: List<Int>): LiveData<List<Student>> {
        val students = MutableLiveData<List<Student>>()
        viewModelScope.launch {
            try {
                Log.d("StudentViewModel", "Fetching students for ids: $student_id")
                val studentsList = studentRepository.getStudentsByIds(student_id)
                Log.d("StudentViewModel", "Fetched students: $studentsList")
                students.value = studentsList
            } catch (e: Exception) {
                _error.value = "Failed to load students by IDs"
                Log.e("StudentViewModel", "Exception: ${e.message}")
            }
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
                _error.value = "Failed to load teacher relations"
            }
        }
        return teacherRelations
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
                _error.value = "Failed to load students by teacher ID"
            }
        }
        return students
    }
}
