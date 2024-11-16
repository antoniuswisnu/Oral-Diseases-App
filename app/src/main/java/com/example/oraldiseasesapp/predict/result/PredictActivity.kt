package com.example.oraldiseasesapp.predict.result

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.oraldiseasesapp.databinding.ActivityPredictBinding

class PredictActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val predictedClass = intent.getStringExtra("predictedClass") ?: "Unknown"
        val confidence = intent.getFloatExtra("confidence", 0f)
        val imageUri = intent.getStringExtra("imageUri")

        displayPredictionResult(predictedClass, confidence, imageUri)
    }

    private fun displayPredictionResult(predictedClass: String, confidence: Float, imageUri: String?) {
        binding.tvTitlePredict.text = "Diagnosis: $predictedClass"
        binding.tvDescPredict.text = "Confidence: ${String.format("%.2f", confidence)}%"

        // Tampilkan gambar hasil prediksi
        if (imageUri != null) {
            try {
                val uri = Uri.parse(imageUri)
                binding.previewImageView.setImageURI(uri)
            } catch (e: Exception) {
                Log.e("PredictActivity", "Failed to load image: ${e.message}")
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No image available", Toast.LENGTH_SHORT).show()
        }
    }
}
