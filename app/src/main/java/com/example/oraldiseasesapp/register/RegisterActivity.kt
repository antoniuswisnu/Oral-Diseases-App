package com.example.oraldiseasesapp.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.login.LoginActivity
import com.example.oraldiseasesapp.data.DatabaseHelper
import com.example.oraldiseasesapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        binding.registerButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

            if (username.isNotEmpty() || password.isNotEmpty()) {
                if (password != confirmPassword) {
                    Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
                } else {
                    if (dbHelper.registerUser(username, password)) {
                        Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                    } else {
                        Toast.makeText(this, "Registrasi gagal", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show()
            }

            binding.sudahPunyaAkunTextView.setOnClickListener {
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            }
        }
    }
}