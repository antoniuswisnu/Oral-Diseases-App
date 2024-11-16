package com.example.oraldiseasesapp.predict.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Preview
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.databinding.ActivityPredictBinding
import com.example.oraldiseasesapp.login.LoginActivity
import com.example.oraldiseasesapp.predict.PreviewActivity

class PredictActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPredictBinding
    private val listDesc = mapOf(
        "Calculus" to "Kalkulus gigi adalah lapisan kotoran yang menempel dan mengeras pada permukaan gigi. Lapisan ini tidak bisa dibersihkan dengan cara disikat. Saat dipegang, kalkulus gigi terasa keras dan memiliki permukaan tidak rata layaknya karang. Karena itulah, kalkulus gigi juga kerap disebut dengan istilah karang gigi.",
        "Caries" to "Karies gigi adalah masalah gigi berlubang, yaitu ketika gigi mengalami kerusakan serta pembusukan di bagian luar dan dalam. Kondisi ini merupakan permasalahan gigi yang dapat menyerang saraf, sering kali karies gigi disebabkan oleh aktivitas bakteri Streptococcus mutans di dalam mulut.",
        "Gingivitis" to "Gingivitis adalah peradangan gusi yang disebabkan oleh penumpukan plak bakteri akibat kebersihan mulut yang buruk. Gejalanya meliputi gusi merah, bengkak, mudah berdarah saat menyikat gigi, serta bau mulut. Jika tidak ditangani, gingivitis bisa berkembang menjadi periodontitis, yang lebih serius dan dapat menyebabkan kerusakan jaringan pendukung gigi.",
        "Ulcer" to "Ulcer dalam konteks kesehatan gigi merujuk pada ulser mulut atau sariawan, yang merupakan luka terbuka kecil di dalam mulut. Ulser ini biasanya berwarna putih atau kekuningan dengan pinggiran merah dan sering menyebabkan rasa sakit, terutama saat makan atau minum.",
        "Tooth Discoloration" to "Tooth Discoloration adalah kondisi di mana warna gigi berubah dan tampak kuning, coklat, abu-abu, atau bahkan hitam. Penyebab utamanya termasuk kebiasaan merokok, konsumsi minuman berpigmen, serta kurangnya kebersihan mulut.",
        "Hypodontia" to "Hypodontia adalah kondisi bawaan di mana seseorang memiliki jumlah gigi permanen yang kurang dari normal karena satu atau lebih gigi tidak berkembang. Ini sering terjadi pada gigi seri kedua atas atau gigi premolar kedua bawah."
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val predictedClass = intent.getStringExtra("predictedClass") ?: "Unknown"
        val confidence = intent.getFloatExtra("confidence", 0f)
        val imageUri = intent.getStringExtra("imageUri")

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
//                val uri = Uri.parse(imageUri)
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
