package com.example.mykidsreg.models

import java.io.Serializable
import java.time.DateTimeException
import com.example.mykidsreg.services.PasswordService

enum class UserType {
    Super_Admin,
    Admin,
    Padagogue,
    Parent
}

data class Users(
    var userId: Int,
    var userName: String,
    var password: String,
    var name: String,
    var lastName: String,
    var address: String,
    var zipCode: Int,
    var email: String,
    var mobilNr: Long,
    var userType: UserType? = null
) : Serializable {

    constructor() : this(
        userId = -1,
        userName = "",
        password = "",
        name = "",
        lastName = "",
        address = "",
        zipCode = 0,
        email = "",
        mobilNr = 0,
    )


    fun UserType.toText(): String {
        return when (this) {
            UserType.Super_Admin -> "Super Admin"
            UserType.Admin -> "Admin"
            UserType.Padagogue -> "Pædagog"
            UserType.Parent -> "Forælder"
        }
    }
}
