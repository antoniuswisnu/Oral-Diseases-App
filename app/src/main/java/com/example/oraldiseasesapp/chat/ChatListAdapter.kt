package com.example.oraldiseasesapp.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.oraldiseasesapp.R

class ChatListAdapter : ListAdapter<ChatMessage, ChatListAdapter.ChatViewHolder>(ChatDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).participant == Participant.USER) {
            R.layout.user_message_item
        } else {
            R.layout.bot_message_item
        }
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        private val participantImageView: ImageView = itemView.findViewById(R.id.participantImageView)

        fun bind(chatMessage: ChatMessage) {
            messageTextView.text = chatMessage.text
            if (chatMessage.participant == Participant.USER) {
                participantImageView.setImageResource(R.drawable.user)
            } else {
                participantImageView.setImageResource(R.drawable.bot)
            }

        }
    }

    class ChatDiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage) = oldItem == newItem
        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage) = oldItem.text == newItem.text
    }
}
