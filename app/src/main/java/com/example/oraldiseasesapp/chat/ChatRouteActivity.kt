package com.example.oraldiseasesapp.chat

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oraldiseasesapp.databinding.ActivityChatRouteBinding

class ChatRouteActivity : AppCompatActivity() {

    private lateinit var chatViewModel: ChatViewModel
    private val adapter = ChatListAdapter()
    private lateinit var binding: ActivityChatRouteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chatViewModel = ViewModelProvider(this, GenerativeViewModelFactory)[ChatViewModel::class.java]

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        binding.chatRecyclerView.adapter = adapter

        chatViewModel.uiStateLiveData.observe(this) { chatUiState : ChatUiState ->
            adapter.submitList(chatUiState.messages.value)
            binding.chatRecyclerView.scrollToPosition(chatUiState.messages.value?.size?.minus(1) ?: 0)
        }

        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text.toString()
            if (message.isNotBlank()) {
                chatViewModel.sendMessage(message)
                binding.messageInput.text.clear()
            }
        }
    }
}