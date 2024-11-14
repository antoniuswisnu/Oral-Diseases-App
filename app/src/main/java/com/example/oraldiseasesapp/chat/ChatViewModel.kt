package com.example.oraldiseasesapp.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(
    private val generativeModel: GenerativeModel
) : ViewModel() {
    private val chat = generativeModel.startChat(
        history = listOf(
            content(role = "user") { text("Hello, I want consult about my oral health.") },
            content(role = "model") { text("Great to meet you. What is your problem?") }
        )
    )

    private val _uiState: MutableStateFlow<ChatUiState> =
        MutableStateFlow(ChatUiState(chat.history.map { content ->
            ChatMessage(
                text = content.parts.first().asTextOrNull() ?: "",
                participant = if (content.role == "user") Participant.USER else Participant.MODEL,
                isPending = false
            )
        }))
    private val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()
    val uiStateLiveData = uiState.asLiveData()

    fun sendMessage(userMessage: String) {
        _uiState.value.addMessage(
            ChatMessage(
                text = userMessage,
                participant = Participant.USER,
                isPending = true
            )
        )

        _uiState.value.addMessage(
            ChatMessage(
                text = "Thinking...",
                participant = Participant.MODEL,
                isPending = true
            )
        )

        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    chat.sendMessage(userMessage)
                }

                _uiState.value.replaceLastPendingMessage()

                response.text?.let { modelResponse ->
                    _uiState.value.addMessage(
                        ChatMessage(
                            text = modelResponse,
                            participant = Participant.MODEL,
                            isPending = false
                        )
                    )
                }

                _uiState.value = _uiState.value

            } catch (e: Exception) {
                _uiState.value.replaceLastPendingMessage()
                e.localizedMessage?.let {
                    val errorMessage = ChatMessage(
                        text = it,
                        participant = Participant.ERROR,
                        isPending = false
                    )
                    _uiState.value.addMessage(errorMessage)
                }
            }
        }
    }


}