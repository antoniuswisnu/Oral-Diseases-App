package com.example.oraldiseasesapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.oraldiseasesapp.camera.CameraActivity
import com.example.oraldiseasesapp.chat.ChatRouteActivity
import com.example.oraldiseasesapp.data.DatabaseHelper
import com.example.oraldiseasesapp.databinding.ActivityMainBinding
import com.example.oraldiseasesapp.login.LoginActivity
import com.example.oraldiseasesapp.profile.ProfileActivity
import com.example.oraldiseasesapp.video.ListVideoActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbHelper: DatabaseHelper

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                openCamera()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        dbHelper = DatabaseHelper(this)

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
            val intent = Intent(this, ChatRouteActivity::class.java)
            startActivity(intent)
        }

        binding.profileImage.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.cardMouth.setOnClickListener {
            if (!allPermissionsGranted()) {
                requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
            } else {
                openCamera()
            }
        }

    }

    private fun openCamera() {
        val intentCameraX = Intent(this, CameraActivity::class.java)
        startActivity(intentCameraX)
    }

}
