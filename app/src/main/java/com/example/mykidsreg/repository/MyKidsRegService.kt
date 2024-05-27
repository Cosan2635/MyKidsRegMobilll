package com.example.mykidsreg.repository

import com.example.mykidsreg.models.User
import com.example.mykidsreg.models.User_type
import com.google.firebase.firestore.auth.User

interface MyKidsRegService {
    suspend fun createUserWithTemporaryPassword(
        username: String,
        name: String,
        last_name: String,
        address: String,
        zip_code: Int,
        email: String,
        mobileNumber: Long,
        userType: User_type
    )

    suspend fun createUser(
        username: String,
        name: String,
        last_name: String,
        address: String,
        zip_code: Int,
        email: String,
        mobileNumber: Long,
        userType: User_type
    )

    suspend fun getUserById(id: Int): User?

    suspend fun getAllUsers(): List<User>

    suspend fun getUserByName(username: String): User?

    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?

    suspend fun updateUser(id: Int, updateUserDto: UpdateUserDTO)

    suspend fun deleteUser(id: Int)
}
