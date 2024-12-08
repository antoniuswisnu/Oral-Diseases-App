package com.example.oraldiseasesapp.predict

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.example.oraldiseasesapp.camera.CameraActivity
import com.example.oraldiseasesapp.camera.CameraActivity.Companion.CAMERAX_RESULT
import com.example.oraldiseasesapp.data.AppDatabase
import com.example.oraldiseasesapp.databinding.ActivityPreviewBinding
import com.example.oraldiseasesapp.ml.ModelNew
import com.example.oraldiseasesapp.report.Prediction
import kotlinx.coroutines.launch
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.exp

class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    private var currentImageUri: Uri? = null
    private var bitmap: Bitmap? = null

    private val listDesc = mapOf(
        "Calculus" to "Kalkulus gigi adalah lapisan kotoran yang menempel dan mengeras pada permukaan gigi. Lapisan ini tidak bisa dibersihkan dengan cara disikat. Saat dipegang, kalkulus gigi terasa keras dan memiliki permukaan tidak rata layaknya karang. Karena itulah, kalkulus gigi juga kerap disebut dengan istilah karang gigi.",
        "Caries" to "Karies gigi adalah masalah gigi berlubang, yaitu ketika gigi mengalami kerusakan serta pembusukan di bagian luar dan dalam. Kondisi ini merupakan permasalahan gigi yang dapat menyerang saraf, sering kali karies gigi disebabkan oleh aktivitas bakteri Streptococcus mutans di dalam mulut.",
        "Gingivitis" to "Gingivitis adalah peradangan gusi yang disebabkan oleh penumpukan plak bakteri akibat kebersihan mulut yang buruk. Gejalanya meliputi gusi merah, bengkak, mudah berdarah saat menyikat gigi, serta bau mulut. Jika tidak ditangani, gingivitis bisa berkembang menjadi periodontitis, yang lebih serius dan dapat menyebabkan kerusakan jaringan pendukung gigi.",
        "Ulcer" to "Ulcer dalam konteks kesehatan gigi merujuk pada ulser mulut atau sariawan, yang merupakan luka terbuka kecil di dalam mulut. Ulser ini biasanya berwarna putih atau kekuningan dengan pinggiran merah dan sering menyebabkan rasa sakit, terutama saat makan atau minum.",
        "Tooth Discoloration" to "Tooth Discoloration adalah kondisi di mana warna gigi berubah dan tampak kuning, coklat, abu-abu, atau bahkan hitam. Penyebab utamanya termasuk kebiasaan merokok, konsumsi minuman berpigmen, serta kurangnya kebersihan mulut.",
        "Hypodontia" to "Hypodontia adalah kondisi bawaan di mana seseorang memiliki jumlah gigi permanen yang kurang dari normal karena satu atau lebih gigi tidak berkembang. Ini sering terjadi pada gigi seri kedua atas atau gigi premolar kedua bawah."
    )
    private val predictionList = mutableListOf<Prediction>()


    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (!isGranted) {
            Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
        }
    }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraXButton.setOnClickListener { startCameraX() }
        binding.predictButton.setOnClickListener { predictImage() }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            bitmap = uriToBitmap(uri)
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private val launcherIntentCameraX = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            bitmap = uriToBitmap(currentImageUri!!)
            showImage()
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun showImage() {
        currentImageUri?.let {
            binding.previewImageView.setImageURI(it)
            binding.progressIndicator.visibility = View.GONE
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val originalBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
            originalBitmap.copy(Bitmap.Config.ARGB_8888, true)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun predictImage() {
        if (bitmap == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressIndicator.visibility = View.VISIBLE

        val tensorImage = preprocessImage(bitmap!!)
        val inputBuffer = tensorImage.buffer

        classifyImage(inputBuffer)
    }

    private fun preprocessImage(bitmap: Bitmap): TensorImage {
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(299, 299, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0.0f, 255.0f))
            .build()

        var tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)
        tensorImage = imageProcessor.process(tensorImage)

        return tensorImage
    }

    private fun classifyImage(inputBuffer: ByteBuffer) {
        val model = ModelNew.newInstance(applicationContext)
        Log.d("PreviewActivity", "Model loaded : $model")

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 3, 299, 299), DataType.FLOAT32)
        inputFeature0.loadBuffer(inputBuffer)
        Log.d("PreviewActivity", "Input Feature 0: $inputFeature0")

        val outputs = model.process(inputFeature0)
        Log.d("PreviewActivity", "Outputs: $outputs")
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        Log.d("PreviewActivity", "Output Feature 0: $outputFeature0")
        val confidenceArray = outputFeature0.floatArray.apply { softmax(this) }
        Log.d("PreviewActivity", "Confidence Array: ${confidenceArray.contentToString()}")

        val labels = arrayOf("Calculus", "Caries", "Gingivitis", "Ulcer", "Tooth Discoloration", "Hypodontia")

        val maxIndex = confidenceArray.indices.maxByOrNull { confidenceArray[it] } ?: -1
        Log.d("PreviewActivity", "Max Index: $maxIndex")

        val result = if (maxIndex >= 0) labels[maxIndex] else "Undetected wound"
        Log.d("PreviewActivity", "Predicted label result: $result")

        for (i in confidenceArray.indices) {
            Log.d("PreviewActivity", "Label: ${labels[i]}, Confidence: ${confidenceArray[i]}")
        }

        val intent = Intent(this, PredictActivity::class.java)
        intent.putExtra("prediction_result", result)

        currentImageUri?.let {
            intent.putExtra("image_uri", it.toString())
        }

        lifecycleScope.launch {
            val database = AppDatabase.getInstance(applicationContext)
            val dao = database.predictionDao()

            val prediction = Prediction(
                label = result,
                confidence = confidenceArray[maxIndex],
                description = listDesc[result] ?: "Deskripsi tidak tersedia.",
                imageUri = currentImageUri?.toString()
            )

            dao.insert(prediction)
            Toast.makeText(this@PreviewActivity, "Prediction saved to report", Toast.LENGTH_SHORT).show()
        }

        startActivity(intent)

        model.close()
        binding.progressIndicator.visibility = View.GONE
    }

    private fun softmax(arr: FloatArray) {
        val max = arr.maxOrNull() ?: return
        val exp = arr.map { exp((it - max).toDouble()) }
        val sum = exp.sum()
        for (i in arr.indices) {
            arr[i] = (exp[i] / sum).toFloat()
        }
    }

}
