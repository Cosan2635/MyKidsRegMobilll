package com.example.mykidsreg.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mykidsreg.models.Message
import com.example.mykidsreg.repositories.MessageRepository
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel() {
    private val repository = MessageRepository()

    val messages = MutableLiveData<List<Message>>()
    val sendMessageResult = MutableLiveData<Message>()

    fun getMessages(userId: Int) {
        viewModelScope.launch {
            repository.getMessages(userId) { messagesList ->
                messages.value = messagesList
            }
        }
    }

    fun sendMessage(message: Message) {
        viewModelScope.launch {
            repository.sendMessage(message) { messageResponse ->
                sendMessageResult.value = messageResponse
            }
        }
    }
}
