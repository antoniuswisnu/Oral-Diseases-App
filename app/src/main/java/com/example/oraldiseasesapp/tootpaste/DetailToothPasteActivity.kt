package com.example.oraldiseasesapp.tootpaste

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.databinding.ActivityDetailToothPasteBinding

class DetailToothPasteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailToothPasteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailToothPasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")
        val imageResId = intent.getIntExtra("image", -1)

        binding.detailTitle.text = name
        binding.detailDescription.text = description
        binding.detailImage.setImageResource(imageResId)

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, ToothpasteActivity::class.java))
            finish()
        }
    }
}
