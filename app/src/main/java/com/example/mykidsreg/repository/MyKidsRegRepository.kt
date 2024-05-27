package com.example.mykidsreg.repository

import com.example.mykidsreg.models.User
import com.example.mykidsreg.models.UpdateUserDTO
import com.example.mykidsreg.models.User_type
import com.example.mykidsreg.services.MyKidsRegService

class MyKidsRegRepository(private val myKidsRegService: MyKidsRegService) {

    suspend fun createUserWithTemporaryPassword(
        username: String,
        name: String,
        last_name: String,
        address: String,
        zip_code: Int,
        email: String,
        mobileNumber: Long,
        userType: User_type
    ) {
        myKidsRegService.createUserWithTemporaryPassword(
            username,
            name,
            last_name,
            address,
            zip_code,
            email,
            mobileNumber,
            userType
        )
    }

    // Implement other repository methods similarly by calling corresponding service methods...
}
