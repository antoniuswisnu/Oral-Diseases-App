package com.example.oraldiseasesapp.profile

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Contacts.SettingsColumns.KEY
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.data.DatabaseHelper
import com.example.oraldiseasesapp.databinding.ActivityProfileBinding
import com.example.oraldiseasesapp.login.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth


class ProfileActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityProfileBinding
    private lateinit var mSharedPref: SharedPreferences
    private var mLogin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        dbHelper = DatabaseHelper(this)
        val dbUser = dbHelper.getCurrentUser()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val displayName = currentUser.displayName
            binding.tvName.text = "$displayName"
        } else {
            val displayName = dbUser?.username
            binding.tvName.text = displayName
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        mSharedPref = getSharedPreferences("sharedPrefFile", MODE_PRIVATE)
//        mLogin = mSharedPref.getBoolean(KEY, false);

        binding.logoutBtn.setOnClickListener {
            if (auth.currentUser != null) {
                auth.signOut()
                val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
                googleSignInClient.signOut().addOnCompleteListener {
                    val logoutIntent = Intent(this, LoginActivity::class.java)
                    logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(logoutIntent)
                    finish()
                }
            } else {
                val editor: SharedPreferences.Editor = mSharedPref.edit()
                editor.putBoolean(KEY, false)
                editor.apply()
                val logoutIntent = Intent(this, LoginActivity::class.java)
                logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(logoutIntent)
                finish()
            }
        }
    }
}