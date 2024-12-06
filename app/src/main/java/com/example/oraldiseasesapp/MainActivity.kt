package com.example.oraldiseasesapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.chatbot.ChatBotActivity
import com.example.oraldiseasesapp.data.DatabaseHelper
import com.example.oraldiseasesapp.databinding.ActivityMainBinding
import com.example.oraldiseasesapp.news.ListNewsActivity
import com.example.oraldiseasesapp.predict.PreviewActivity
import com.example.oraldiseasesapp.profile.ProfileActivity
import com.example.oraldiseasesapp.report.ReportActivity
import com.example.oraldiseasesapp.tootpaste.ToothpasteActivity
import com.example.oraldiseasesapp.video.ListVideoActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        dbHelper = DatabaseHelper(this)

        val currentUser = auth.currentUser
        val dbUser = dbHelper.getCurrentUser()

        if (currentUser != null) {
            val displayName = currentUser.displayName
            binding.tvUsername.text = displayName ?: "Firebase User"
        } else if (dbUser != null) {
            val displayName = dbUser.username
            binding.tvUsername.text = displayName
        }

        binding.vids.setOnClickListener {
            val intent = Intent(this, ListVideoActivity::class.java)
            startActivity(intent)
        }

        binding.chatbot.setOnClickListener {
            val intent = Intent(this, ChatBotActivity::class.java)
            startActivity(intent)
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.cardMouth.setOnClickListener {
            val intent = Intent(this, PreviewActivity::class.java)
            startActivity(intent)
        }

        binding.pods.setOnClickListener {
            Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.stats.setOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }

        binding.articles.setOnClickListener {
            startActivity(Intent(this, ListNewsActivity::class.java))
        }

        binding.cardTooth.setOnClickListener {
            val intent = Intent(this, ToothpasteActivity::class.java)
            startActivity(intent)
        }

    }
}
