    package com.example.mykidsreg.models

    import android.util.Log
    import com.squareup.moshi.Json
    import com.squareup.moshi.FromJson
    import com.squareup.moshi.ToJson
    import java.io.Serializable

    enum class UserType(val value: Int) {
        Super_Admin(0),
        Admin(1),
        Padagogue(2),
        Parent(3);

        companion object {
            private val map = values().associateBy(UserType::value)
            fun fromInt(type: Int) = map[type]
        }
    }

    data class Users(
        @Json(name = "user_Id") var user_Id: Int,
        @Json(name = "user_Name") var user_Name: String,
        var password: String,
        var name: String,
        @Json(name = "last_name") var last_name: String,
        var address: String,
        @Json(name = "zip_code") var zip_code: Int,
        @Json(name = "e_mail") var e_mail: String,
        @Json(name = "mobil_nr") var mobil_nr: Long,
        @Json(name = "usertype") var usertype: UserType? = null
    ) : Serializable {

        constructor() : this(
            user_Id = -1,
            user_Name = "",
            password = "",
            name = "",
            last_name = "",
            address = "",
            zip_code = 0,
            e_mail = "",
            mobil_nr = 0,
        )

        fun userTypeToInt(): Int {
            return usertype?.value ?: -1
        }

        fun setUserTypeFromInt(value: Int) {
            usertype = UserType.fromInt(value)
        }

        fun userTypeToString(): String {
            return usertype?.name ?: ""
        }

        override fun toString(): String {
            return "Users(userId=$user_Id, userName='$user_Name', password='$password', name='$name', lastName='$last_name'," +
                    " address='$address', zipCode=$zip_code, email='$e_mail', mobilNr=$mobil_nr, userType=${usertype?.name})"
        }
    }

    class UserTypeAdapter {
        @ToJson
        fun toJson(usertype: UserType): Int {
            return usertype.value
        }

        @FromJson
        fun fromJson(value: Int): UserType? {
            val usertype = UserType.fromInt(value)
            Log.d("UserTypeAdapter", "Deserialized userType: $usertype from value: $value")
            return usertype
        }
    }
