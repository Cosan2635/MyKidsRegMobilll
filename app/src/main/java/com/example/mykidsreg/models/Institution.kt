package com.example.mykidsreg.models

import java.io.Serializable

data class Institution(
    var id: Int,
    var name: String,
    var address: String,
    var zipCode: Int,
    var tlfNumber: Long,
    var departments: List<Department> = listOf(),
    var messages: List<Message> = listOf()
) : Serializable {
    constructor(name: String, address: String, zipCode: Int,tlfNumber: Long) : this(
        id = -1,
        name = name,
        address = address,
        zipCode = zipCode,
        tlfNumber = tlfNumber
    )
}