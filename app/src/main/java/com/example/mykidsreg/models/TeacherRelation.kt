package com.example.mykidsreg.models
import java.io.Serializable

data class TeacherRelation(
    var id: Int,
    var user_id: Int,
    var department_id: Int
) : Serializable {
    constructor(teacherId: Int, departmentId: Int) : this(-1, teacherId, departmentId)
}