package com.example.mykidsreg.models
import java.io.Serializable

data class TeacherRelation(
    var id: Int,
    var teacherId: Int,
    var departmentId: Int
) : Serializable {
    constructor(teacherId: Int, departmentId: Int) : this(-1, teacherId, departmentId)
}