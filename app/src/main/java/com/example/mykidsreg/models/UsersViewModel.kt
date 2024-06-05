// UserViewModel.kt
package com.example.mykidsreg.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mykidsreg.models.UserType
import com.example.mykidsreg.models.Users
import com.example.mykidsreg.repository.UserRepository

class UserViewModel : ViewModel() {
    private val userRepository = UserRepository()

    val errorMessageLiveData: LiveData<String>
        get() = userRepository.errorMessageLiveData
    val loginResult: LiveData<Users>
        get() = userRepository.loginResult

    fun login(username: String, password: String) {
        userRepository.getUserByUsernameAndPassword(username, password)
    }

    fun getUserById(userId: String) {
        userRepository.GetUserById(userId)
    }

    fun getUsersByType(Usertype: UserType) {
        userRepository.getUsersByType(Usertype.value)
    }

    fun getPedagogues() {
        userRepository.getPedagogues()
    }

    fun getParents() {
        userRepository.getParents()
    }

    fun deleteUser(userId: Int) {
        userRepository.deleteUser(userId)
    }

    fun updateUser(userId: Int, user: Users) {
        userRepository.updateUser(userId, user)
    }
}
