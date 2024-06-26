package com.example.mykidsreg.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TeacherRelation(
    @SerializedName("id")
    var id: Int,
    @SerializedName("user_id")
    var userId: Int,
    @SerializedName("department_id")
    var departmentId: Int
) : Serializable {
    constructor(teacherId: Int, departmentId: Int) : this(-1, teacherId, departmentId)
}
