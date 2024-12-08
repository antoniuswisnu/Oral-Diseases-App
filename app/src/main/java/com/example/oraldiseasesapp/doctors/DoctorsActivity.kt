package com.example.oraldiseasesapp.doctors

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.R
import com.example.oraldiseasesapp.databinding.ActivityDoctorsBinding
import kotlin.random.Random

class DoctorsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDoctorsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val doctorImages = listOf(
            R.drawable.doktor,
            R.drawable.doktor_2,
            R.drawable.doktor_3,
            R.drawable.doktor_4,
            R.drawable.doktor_5,
            R.drawable.doktor_6
        )


        val doctors = listOf(
            Doctor("Dr. Ahmad Fauzan", "Sp. Gigi dan Mulut", "12 tahun", "96%", "Rp50.000", doctorImages.random()),
            Doctor("Dr. Farah Annisa", "Sp. Gigi dan Mulut", "10 tahun", "98%", "Rp55.000", doctorImages.random()),
            Doctor("Dr. Budi Santoso", "Sp. Gigi dan Mulut", "8 tahun", "94%", "Rp45.000", doctorImages.random()),
            Doctor("Dr. Intan Sari", "Sp. Gigi dan Mulut", "15 tahun", "97%", "Rp60.000", doctorImages.random()),
            Doctor("Dr. Rian Hakim", "Sp. Gigi dan Mulut", "9 tahun", "95%", "Rp50.000", doctorImages.random()),
            Doctor("Dr. Diana Kartini", "Sp. Gigi dan Mulut", "7 tahun", "93%", "Rp40.000", doctorImages.random()),
            Doctor("Dr. Faisal Ramadhan", "Sp. Gigi dan Mulut", "11 tahun", "96%", "Rp52.000", doctorImages.random()),
            Doctor("Dr. Melisa Anggraini", "Sp. Gigi dan Mulut", "13 tahun", "99%", "Rp58.000", doctorImages.random()),
            Doctor("Dr. Dimas Setiawan", "Sp. Gigi dan Mulut", "6 tahun", "92%", "Rp43.000", doctorImages.random()),
            Doctor("Dr. Laila Nurhaliza", "Sp. Gigi dan Mulut", "14 tahun", "98%", "Rp65.000", doctorImages.random())
        )

        binding.rvDoctors.layoutManager = LinearLayoutManager(this)
        val adapter = DoctorAdapter(doctors)
        binding.rvDoctors.adapter = adapter

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}