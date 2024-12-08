package com.example.oraldiseasesapp.predict

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.databinding.ActivityPredictBinding

class PredictActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictBinding
    private val listDesc = mapOf(
        "Calculus" to "Kalkulus gigi adalah lapisan kotoran yang mengeras pada permukaan gigi. Lapisan ini tidak bisa dibersihkan dengan cara disikat, hanya dapat dihilangkan dengan perawatan profesional.",
        "Tooth Discoloration" to "Perubahan warna gigi yang sering disebabkan oleh konsumsi makanan berpigmen, minuman seperti kopi atau teh, kebiasaan merokok, atau kurangnya kebersihan mulut.",
        "Healthy Teeth" to "Gigi sehat adalah kondisi ideal di mana gigi bersih, tidak berlubang, bebas dari plak, dan tidak mengalami kerusakan atau peradangan.",
        "Caries" to "Karies adalah kerusakan gigi akibat bakteri yang menyebabkan gigi berlubang. Biasanya terjadi karena kebersihan mulut yang buruk atau konsumsi makanan tinggi gula.",
        "Ulcer" to "Ulcer atau sariawan adalah luka terbuka kecil di dalam mulut yang sering menyebabkan rasa sakit, terutama saat makan atau minum. Ulser biasanya tidak serius dan dapat sembuh dalam beberapa hari.",
        "Gingivitis" to "Gingivitis adalah peradangan gusi akibat penumpukan plak di sekitar gigi. Gejalanya meliputi gusi merah, bengkak, mudah berdarah, dan bau mulut.",
        "Hypodontia" to "Hypodontia adalah kondisi di mana satu atau lebih gigi permanen tidak berkembang. Ini merupakan kelainan bawaan yang sering terjadi pada gigi seri atau premolar."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayPredictionResult()

        binding.btnFinish.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnTryAgain.setOnClickListener{
            val intent = Intent(this, PreviewActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


    private fun displayPredictionResult() {
        val predictionResult = intent.getStringExtra("prediction_result") ?: "No result"
        val imageUriString = intent.getStringExtra("image_uri")
        val imageUri = imageUriString?.toUri()

        binding.tvTitlePredict.text = "Diagnosis: $predictionResult"
        val description = listDesc[predictionResult] ?: "Deskripsi tidak tersedia untuk hasil ini."
        binding.tvDescPredict.text = description

        if (imageUri != null) {
            try {
                binding.previewImageView.setImageURI(imageUri)
            } catch (e: Exception) {
                Log.e("PredictActivity", "Failed to load image: ${e.message}")
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No image available", Toast.LENGTH_SHORT).show()
        }
    }

}
