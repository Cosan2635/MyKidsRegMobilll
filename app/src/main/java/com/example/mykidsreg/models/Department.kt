package com.example.mykidsreg.models

import Student
import java.io.Serializable
data class Department(
    var id: Int,
    var name: String,
    var institutionId: Int,
    var students: List<Student> = listOf(),
    var teacherRelation: List<TeacherRelation> = listOf()
) : Serializable {
    constructor(name: String, institutionId: Int) : this(
        id = -1,
        name = name,
        institutionId = institutionId
    )
}