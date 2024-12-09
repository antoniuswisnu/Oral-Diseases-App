package com.example.oraldiseasesapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.chatbot.ChatBotActivity
import com.example.oraldiseasesapp.clinics.ClinicActivity
import com.example.oraldiseasesapp.data.DatabaseHelper
import com.example.oraldiseasesapp.databinding.ActivityMainBinding
import com.example.oraldiseasesapp.doctors.DoctorsActivity
import com.example.oraldiseasesapp.info.InfoActivity
import com.example.oraldiseasesapp.news.ListNewsActivity
import com.example.oraldiseasesapp.predict.PreviewActivity
import com.example.oraldiseasesapp.predict.ScanActivity
import com.example.oraldiseasesapp.profile.ProfileActivity
import com.example.oraldiseasesapp.report.ReportActivity
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
            startActivity(Intent(this, ListVideoActivity::class.java))
        }

        binding.chatbot.setOnClickListener {
            startActivity(Intent(this, ChatBotActivity::class.java))
        }

        binding.profileImage.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        binding.cardMouth.setOnClickListener {
            startActivity(Intent(this, PreviewActivity::class.java))
        }

        binding.stats.setOnClickListener {
            startActivity(Intent(this, ReportActivity::class.java))
        }

        binding.articles.setOnClickListener {
            startActivity(Intent(this, ListNewsActivity::class.java))
        }

        binding.cardInfo.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
        }

        binding.clinics.setOnClickListener {
            startActivity(Intent(this, ClinicActivity::class.java))
        }

        binding.doctors.setOnClickListener {
            startActivity(Intent(this, DoctorsActivity::class.java))
        }

        binding.btnScan.setOnClickListener {
            startActivity(Intent(this, ScanActivity::class.java))
        }

    }
}
