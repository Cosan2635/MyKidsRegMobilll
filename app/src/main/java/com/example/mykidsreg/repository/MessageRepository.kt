package com.example.mykidsreg.repositories

import com.example.mykidsreg.models.Message

import com.example.mykidsreg.services.ApiClient.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageRepository {


    fun getMessages(userId: Int, onResult: (List<Message>?) -> Unit) {
        apiService.getMessages(userId).enqueue(object : Callback<List<Message>> {
            override fun onResponse(call: Call<List<Message>>, response: Response<List<Message>>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun sendMessage(message: Message, onResult: (Message?) -> Unit) {
        apiService.sendMessage(message).enqueue(object : Callback<Message> {
            override fun onResponse(call: Call<Message>, response: Response<Message>) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<Message>, t: Throwable) {
                onResult(null)
            }
        })
    }
}
