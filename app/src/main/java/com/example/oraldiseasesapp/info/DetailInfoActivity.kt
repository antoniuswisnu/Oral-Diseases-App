package com.example.oraldiseasesapp.info

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.databinding.ActivityDetailInfoBinding

class DetailInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val imageUrl = intent.getIntExtra("image_url", 0)
        val titleObat = intent.getStringExtra("titleObat")
        val contentObat = intent.getStringExtra("contentObat")
        val imageUrlObat = intent.getIntExtra("image_urlObat", 0)

        binding.apply{
            tvTitle.text = title
            tvDesc.text = content
            ivOral.setImageResource(imageUrl)
            tvObatTitle1.text = titleObat
            tvObatDesc1.text = contentObat
            ivObat1.setImageResource(imageUrlObat)
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
            finish()
        }
    }
}