package com.example.oraldiseasesapp.info

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.R
import com.example.oraldiseasesapp.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            normal.setOnClickListener {
                startDetailActivity("Normal", "Description for Normal", R.drawable.normal, "Obat Normal", "Title Obat Normal", "Desc Obat Normal", "http://example.com/normal")
            }
            caries.setOnClickListener {
                startDetailActivity("Caries", "Description for Caries", R.drawable.caries, "Obat Caries", "Title Obat Caries", "Desc Obat Caries", "http://example.com/caries")
            }
            gingivitis.setOnClickListener {
                startDetailActivity("Gingivitis", "Description for Gingivitis", R.drawable.gingivitis, "Obat Gingivitis", "Title Obat Gingivitis", "Desc Obat Gingivitis", "http://example.com/gingivitis")
            }
            hypodontia.setOnClickListener {
                startDetailActivity("Hypodontia", "Description for Hypodontia", R.drawable.hypodontia, "Obat Hypodontia", "Title Obat Hypodontia", "Desc Obat Hypodontia", "http://example.com/hypodontia")
            }
            calculus.setOnClickListener {
                startDetailActivity("Calculus", "Description for Calculus", R.drawable.calculus, "Obat Calculus", "Title Obat Calculus", "Desc Obat Calculus", "http://example.com/calculus")
            }
            ulcer.setOnClickListener {
                startDetailActivity("Ulcer", "Description for Ulcer", R.drawable.ulcer, "Obat Ulcer", "Title Obat Ulcer", "Desc Obat Ulcer", "http://example.com/ulcer")
            }
            discoloration.setOnClickListener {
                startDetailActivity("Discoloration", "Description for Discoloration", R.drawable.discoloration, "Obat Discoloration", "Title Obat Discoloration", "Desc Obat Discoloration", "http://example.com/discoloration")
            }
        }
    }

    private fun startDetailActivity(title: String, desc: String, imageRes: Int, obat: String, titleObat: String, descObat: String, url: String) {
        val intent = Intent(this, DetailInfoActivity::class.java).apply {
            putExtra("TITLE", title)
            putExtra("DESC", desc)
            putExtra("IMAGE_RES", imageRes)
            putExtra("OBAT", obat)
            putExtra("TITLE_OBAT", titleObat)
            putExtra("DESC_OBAT", descObat)
            putExtra("URL", url)
        }
        startActivity(intent)
    }
}