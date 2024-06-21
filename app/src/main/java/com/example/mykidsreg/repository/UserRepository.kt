package com.example.mykidsreg.repository


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mykidsreg.models.UserType
import com.example.mykidsreg.models.UserTypeAdapter
import com.example.mykidsreg.models.Users
import com.example.mykidsreg.services.ApiClient
import com.example.mykidsreg.services.LoginRequest
import com.example.mykidsreg.services.LoginResponse
import com.example.mykidsreg.services.UserService
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class UserRepository {
    private val url = "http://192.168.1.130:5191/api/Users/"
    private val userService: UserService
    val userLiveData: MutableLiveData<List<Users>> = MutableLiveData()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()
    private val _loginResult = MutableLiveData<Users>()
    val loginResult: LiveData<Users>
        get() = _loginResult

    init {
        val moshi = Moshi.Builder()
            .add(UserTypeAdapter())
            .build()

        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
        userService = build.create(UserService::class.java)
    }

    fun getUserByUsernameAndPassword(username: String, password: String): LiveData<Users> {
        val loginRequest = LoginRequest(username, password)
        Log.d("LOGIN", "Sending login request: $loginRequest")
        ApiClient.apiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LOGIN", "Login response received: ${response.code()} - ${response.message()}")
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Log.d("LOGIN", "LoginResponse body: $loginResponse")
                    if (loginResponse != null) {
                        fetchUserDetails(loginResponse.user_id)
                    } else {
                        Log.e("LOGIN", "Login failed: loginResponse is null")
                        errorMessageLiveData.postValue("Login failed: loginResponse is null")
                        _loginResult.postValue(Users()) // Post a default Users object
                    }
                } else {
                    Log.e("LOGIN", "Login failed: ${response.message()}")
                    errorMessageLiveData.postValue("Login failed: " + response.message())
                    _loginResult.postValue(Users()) // Post a default Users object
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LOGIN", "Login request failed: ${t.message}")
                errorMessageLiveData.postValue(t.message)
                _loginResult.postValue(Users()) // Post a default Users object
            }
        })
        return loginResult
    }

    private fun fetchUserDetails(id: Int) {
        Log.d("LOGIN", "Fetching user details for user id: $id")
        ApiClient.apiService.getUserInfo(id).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                Log.d("LOGIN", "User details response received: ${response.code()} - ${response.message()}")
                if (response.isSuccessful) {
                    val user = response.body()
                    Log.d("LOGIN", "Fetched User Details: $user")
                    user?.let {
                        Log.d("LOGIN", "Before setting userType: ${it.usertype}")
                        it.usertype = UserType.fromInt(it.userTypeToInt()) // Set userType
                        Log.d("LOGIN", "After setting userType: ${it.usertype}")
                        _loginResult.postValue(it)
                        errorMessageLiveData.postValue("")
                    } ?: run {
                        Log.e("LOGIN", "User data is null")
                        errorMessageLiveData.postValue("User data is null")
                        _loginResult.postValue(Users()) // Post a default Users object
                    }
                } else {
                    Log.e("LOGIN", "Fetching user details failed: ${response.message()}")
                    errorMessageLiveData.postValue(response.message())
                    _loginResult.postValue(Users()) // Post a default Users object
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Log.e("LOGIN", "Fetching user details request failed: ${t.message}")
                errorMessageLiveData.postValue(t.message)
                _loginResult.postValue(Users()) // Post a default Users object
            }
        })
    }

    fun GetUserById(user_id: String) {
        Log.d("APPEL", "Getting user by id: $user_id")
        userService.GetUser(user_id).enqueue(object : Callback<List<Users>> {
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                Log.d("APPEL", "Get user by id response received: ${response.code()} - ${response.message()}")
                if (response.isSuccessful) {
                    val b: List<Users>? = response.body()
                    Log.d("APPEL", "Received users: $b")
                    userLiveData.postValue(b!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    Log.e("APPEL", "Get user by id failed: $message")
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Log.e("APPEL", "Get user by id request failed: ${t.message}")
                errorMessageLiveData.postValue(t.message)
            }
        })
    }

    fun getUsersByType(usertype: Int) {
        Log.d("APPEL", "Getting users by type: $usertype")
        userService.getUsersByType(usertype).enqueue(object : Callback<List<Users>> {
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                Log.d("APPEL", "Get users by type response received: ${response.code()} - ${response.message()}")
                if (response.isSuccessful) {
                    val users: List<Users>? = response.body()
                    Log.d("APPEL", "Received users by type: $users")
                    userLiveData.postValue(users!!)
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    Log.e("APPEL", "Get users by type failed: $message")
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Log.e("APPEL", "Get users by type request failed: ${t.message}")
                errorMessageLiveData.postValue(t.message)
            }
        })
    }

    fun getPedagogues() {
        Log.d("APPEL", "Getting pedagogues")
        getUsersByType(2)
    }

    fun getParents() {
        Log.d("APPEL", "Getting parents")
        getUsersByType(3)
    }

    fun deleteUser(userId: Int) {
        Log.d("APPEL", "Deleting user with id: $userId")
        userService.delete(userId).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                Log.d("APPEL", "Delete user response received: ${response.code()} - ${response.message()}")
                if (response.isSuccessful) {
                    Log.d("APPEL", "User deleted successfully")
                    updateMessageLiveData.postValue("User deleted successfully")
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    Log.e("APPEL", "Delete user failed: $message")
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Log.e("APPEL", "Delete user request failed: ${t.message}")
                errorMessageLiveData.postValue(t.message)
            }
        })
    }

    fun updateUser(userId: Int, user: Users) {
        Log.d("APPEL", "Updating user with id: $userId, user: $user")
        userService.udateuser(userId, user).enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                Log.d("APPEL", "Update user response received: ${response.code()} - ${response.message()}")
                if (response.isSuccessful) {
                    Log.d("APPEL", "User updated successfully")
                    updateMessageLiveData.postValue("User updated successfully")
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    Log.e("APPEL", "Update user failed: $message")
                    errorMessageLiveData.postValue(message)
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                Log.e("APPEL", "Update user request failed: ${t.message}")
                errorMessageLiveData.postValue(t.message)
            }
        })
    }
}
