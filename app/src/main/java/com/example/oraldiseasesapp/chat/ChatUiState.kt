package com.example.oraldiseasesapp.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatUiState(
    initialMessages: List<ChatMessage> = emptyList()
) : ViewModel() {

    private val _messages = MutableLiveData<MutableList<ChatMessage>>().apply {
        value = initialMessages.toMutableList()
    }
    val messages: MutableLiveData<MutableList<ChatMessage>> = _messages

    fun addMessage(msg: ChatMessage) {
        _messages.value?.apply {
            add(msg)
            _messages.value = this
        }
    }

    fun replaceLastPendingMessage() {
        _messages.value?.let { currentMessages ->
            val lastMessage = currentMessages.lastOrNull()
            lastMessage?.let {
                val newMessage = lastMessage.copy(isPending = false)
                currentMessages.removeAt(currentMessages.lastIndex)
                currentMessages.add(newMessage)
                _messages.value = currentMessages
            }
        }
    }
}
