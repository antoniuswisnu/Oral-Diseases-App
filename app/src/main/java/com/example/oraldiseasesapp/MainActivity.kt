package com.example.oraldiseasesapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.buttonSignin.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        // Launch Google Sign-In intent
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        } else {
            Toast.makeText(this, "Sign-in failed. Please try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account = task.result
            if (account != null) {
                updateUI(account)
            }
        } else {
            Toast.makeText(this, "Google Sign-In failed: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)

        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                navigateToHome(account)
            } else {
                Toast.makeText(this, "Authentication failed: ${task.exception?.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHome(account: GoogleSignInAccount) {
        val intent = Intent(this, HomeActivity::class.java).apply {
            putExtra("email", account.email)
            putExtra("name", account.displayName)
        }
        startActivity(intent)
        finish()
    }
}
