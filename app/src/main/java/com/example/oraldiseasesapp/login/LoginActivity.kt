package com.example.oraldiseasesapp.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.R
import com.example.oraldiseasesapp.data.DatabaseHelper
import com.example.oraldiseasesapp.databinding.ActivityLoginBinding
import com.example.oraldiseasesapp.onboarding.OnBoardingActivity
import com.example.oraldiseasesapp.register.RegisterActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityLoginBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.buttonGoogleSignin.setOnClickListener {
            signInWithGoogle()
        }

        binding.tvRegis.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        dbHelper = DatabaseHelper(this)

        binding.buttonSignin.setOnClickListener {
            val username = binding.username.text.toString().trim()
            val password = binding.password.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                val isValidUser = dbHelper.loginUser(username, password)
                if (isValidUser) {
                    val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
                    sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                    Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show()
                    val mainIntent = Intent(this, OnBoardingActivity::class.java).apply {
                        putExtra("name", username)
                    }
                    startActivity(mainIntent)
                    finish()
                }
                else {
                    Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        } else {
            Log.d("LoginActivity", result.resultCode.toString() )
            Log.d("LoginActivity", result.toString() )
            Log.d("LoginActivity", result.data.toString() )
            Toast.makeText(this, "Sign-in failed. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account = task.result
            if (account != null) {
                Log.d("LoginActivity", "Google Sign-In successful. Account: ${account.email}")
                updateUI(account)
            } else {
                Log.d("LoginActivity", "GoogleSignInAccount is null.")
                Toast.makeText(this, "Google Sign-In account is null.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e("LoginActivity", "Google Sign-In failed: ${task.exception?.localizedMessage}")
            task.exception?.printStackTrace()
            Toast.makeText(this, "Google Sign-In failed: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateUI(account: GoogleSignInAccount) {
        val idToken = account.idToken
        if (idToken == null) {
            Log.e("LoginActivity", "ID token is null. Cannot authenticate with Firebase.")
            Toast.makeText(this, "ID token is null. Please try again.", Toast.LENGTH_SHORT).show()
            return
        }

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        Log.d("LoginActivity", "ID Token: $idToken")

        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("LoginActivity", "Firebase authentication successful.")
                navigateToMain(account)
            } else {
                Log.e("LoginActivity", "Firebase authentication failed: ${task.exception?.localizedMessage}")
                task.exception?.printStackTrace()
                Toast.makeText(this, "Authentication failed: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun navigateToMain(account: GoogleSignInAccount) {
        val sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

        val intent = Intent(this, OnBoardingActivity::class.java).apply {
            putExtra("email", account.email)
            putExtra("name", account.displayName)
        }
        startActivity(intent)
        finish()
    }


}
