package com.example.oraldiseasesapp.info

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.databinding.ActivityDetailInfoBinding

class DetailInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailInfoBinding

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("TITLE")
        val content = intent.getStringExtra("DESC")
        val imageUrl = intent.getIntExtra("IMAGE_RES", 0)
        val titleObat = intent.getStringExtra("TITLE_OBAT")
        val contentObat = intent.getStringExtra("DESC_OBAT")
        val obatUrl = intent.getStringExtra("URL_OBAT")

        binding.apply{
            tvTitle.text = title
            tvDesc.text = content
            ivOral.setImageResource(imageUrl)
            tvObatTitle1.text = titleObat
            tvObatDesc1.text = contentObat
        }

        binding.ivShopping1.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(obatUrl))
            startActivity(intent)
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
            finish()
        }
    }
}