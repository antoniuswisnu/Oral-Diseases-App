package com.example.oraldiseasesapp.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.databinding.ActivityDetailInfoBinding

class DetailInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}