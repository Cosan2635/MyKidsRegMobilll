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

    init {
        validate()
    }

    private fun validate() {
        nameValidate()
        lastNameValidate()
    }

    private fun nameValidate() {
        require(name.isNotBlank()) { "Name must be provided." }
        require(name.length in 2..15) { "Name must be between 2 and 15 characters." }
    }

    private fun lastNameValidate() {
        require(lastName.isNotBlank()) { "Last name must be provided." }
        require(lastName.length in 2..15) { "Last name must be between 2 and 15 characters." }
    }

    override fun toString(): String {
        return "$id, $name, $lastName, $birthday, $departmentId"
    }
}

// Assuming ParentsRelation and StudentLog are defined similarly

