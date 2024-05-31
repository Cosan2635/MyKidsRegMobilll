package com.example.mykidsreg.models

import java.io.Serializable

data class Student(
    var id: Int,
    var name: String,
    var lastName: String,
    var birthday: String,
    var departmentId: Int?,
    var parentsRelations: List<ParentsRelation> = listOf(),
    var studentLogs: List<StudentLog> = listOf()
) : Serializable {
    constructor(
        name: String,
        lastName: String,
        birthday: String
    ) : this(-1, name, lastName, birthday, null)

    override fun toString(): String {
        return "$id, $name, $lastName, $birthday, $departmentId"
    }
}
