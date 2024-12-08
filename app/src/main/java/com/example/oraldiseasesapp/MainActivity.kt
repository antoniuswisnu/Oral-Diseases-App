package com.example.oraldiseasesapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.oraldiseasesapp.camera.CameraActivity
import com.example.oraldiseasesapp.chat.ChatRouteActivity
import com.example.oraldiseasesapp.chatbot.data.ChatBotActivity
import com.example.oraldiseasesapp.clinics.ClinicActivity
import com.example.oraldiseasesapp.data.DatabaseHelper
import com.example.oraldiseasesapp.databinding.ActivityMainBinding
import com.example.oraldiseasesapp.doctors.DoctorsActivity
import com.example.oraldiseasesapp.login.LoginActivity
import com.example.oraldiseasesapp.predict.PreviewActivity
import com.example.oraldiseasesapp.profile.ProfileActivity
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

        checkIfUserIsLoggedIn()

        val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        val currentUser = auth.currentUser
        val dbUser = dbHelper.getCurrentUser()

        // logic masih coba"
        if (currentUser != null || isLoggedIn) {
            val displayName = currentUser?.displayName
            binding.tvUsername.text = displayName ?: "Firebase User"
        } else if (dbUser != null) {
            val displayName = dbUser.username
            binding.tvUsername.text = displayName
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
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

        binding.btnScan.setOnClickListener {
            val intent = Intent(this, PreviewActivity::class.java)
            startActivity(intent)
        }

        binding.clinics.setOnClickListener {
            val intent = Intent(this, ClinicActivity::class.java)
            startActivity(intent)
        }

        binding.stats.setOnClickListener {
            Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.articles.setOnClickListener {
            Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.cardTooth.setOnClickListener {
            val intent = Intent(this, ToothpasteActivity::class.java)
            startActivity(intent)
        }

        binding.doctor.setOnClickListener{
            val intent = Intent(this, DoctorsActivity::class.java)
            startActivity(intent)
        }

    }

    private fun checkIfUserIsLoggedIn() {
        val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (auth.currentUser != null) {
            val displayName = auth.currentUser?.displayName ?: "Firebase User"
            binding.tvUsername.text = displayName
        } else if (isLoggedIn) {
            val dbUser = dbHelper.getCurrentUser()
            if (dbUser != null) {
                binding.tvUsername.text = dbUser.username
            } else {
                navigateToLogin()
            }
        } else {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}
