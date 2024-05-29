package com.example.mykidsreg.models
import java.io.Serializable
data class ParentsRelation(
    var id: Int,
    var userId: Int,
    var studentId: Int
) : Serializable{
    constructor(userId: Int, studentId: Int) : this(-1, userId, studentId)
}