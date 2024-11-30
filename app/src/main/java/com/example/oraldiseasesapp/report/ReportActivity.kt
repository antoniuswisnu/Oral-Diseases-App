package com.example.oraldiseasesapp.report

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.data.AppDatabase
import com.example.oraldiseasesapp.databinding.ActivityReportBinding
import kotlinx.coroutines.launch

class ReportActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReportBinding
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ReportAdapter(emptyList())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            database = AppDatabase.getInstance(applicationContext)
            database.predictionDao().getAllPredictions().collect { predictions ->
                adapter.updateData(predictions)
            }
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
