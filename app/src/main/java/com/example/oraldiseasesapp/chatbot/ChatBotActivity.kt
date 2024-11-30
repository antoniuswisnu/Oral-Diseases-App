package com.example.oraldiseasesapp.chatbot

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oraldiseasesapp.databinding.ActivityChatBotBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class ChatBotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBotBinding
    private val messageList = mutableListOf<Message>()
    private lateinit var messageAdapter: MessageAdapter

    private val client = OkHttpClient.Builder()
        .callTimeout(60, TimeUnit.SECONDS)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageAdapter = MessageAdapter(messageList)
        binding.rvAiChat.adapter = messageAdapter
        binding.rvAiChat.layoutManager = LinearLayoutManager(this)

        binding.btnChatSend.setOnClickListener {
            val question = binding.etChatMessage.text.toString().trim()
            addToChat(question, Message.SENT_BY_ME)
            binding.etChatMessage.text.clear()
//            binding.animationView.visibility = View.GONE
            callAPI(question)
        }

        hideSystemUI()
    }

    private fun addToChat(message: String, sentBy: String) {
        runOnUiThread {
            messageList.add(Message(message, sentBy))
            messageAdapter.notifyDataSetChanged()
            binding.rvAiChat.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }

    private fun addResponse(response: String) {
        messageList.removeAt(messageList.size - 1)
        addToChat(response, Message.SENT_BY_BOT)
    }

    private fun callAPI(question: String) {
        messageList.add(Message("Typing... ", Message.SENT_BY_BOT))

        val jsonBody = JSONObject()
        try {
            jsonBody.put("message", question)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val JSON: MediaType? = "application/json; charset=utf-8".toMediaTypeOrNull()
//        val api_key = (getString(R.string.openapi_key))

        val body = RequestBody.create(JSON, jsonBody.toString())
        val request = Request.Builder()
            .url("https://chat-bot-api-2tkd.vercel.app/api/chat")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                addResponse("Failed to load response due to ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    try {
                        val responseBodyString = response.body?.string()
                        val jsonObject = JSONObject(responseBodyString)
                        val metaObject = jsonObject.getJSONObject("meta")
                        val status = metaObject.getInt("status")

                        if (status == 200) {
                            val dataObject = jsonObject.getJSONObject("data")
                            val payloadObject = dataObject.getJSONObject("payload")
                            val result = payloadObject.getString("message")
                            addResponse(result.trim())
                        } else {
                            addResponse("Failed to load response. Status: $status")
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    val errorBody = response.body?.string() ?: "Empty error body"
                    addResponse("Failed to load response due to $errorBody")
                }
            }
        })
    }


    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}