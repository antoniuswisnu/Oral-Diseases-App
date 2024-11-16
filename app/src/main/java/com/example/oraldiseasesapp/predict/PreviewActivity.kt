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
import com.example.oraldiseasesapp.camera.CameraActivity
import com.example.oraldiseasesapp.camera.CameraActivity.Companion.CAMERAX_RESULT
import com.example.oraldiseasesapp.databinding.ActivityPreviewBinding
import com.example.oraldiseasesapp.ml.ModelNew
import com.example.oraldiseasesapp.predict.result.PredictActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class PreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreviewBinding
    private var currentImageUri: Uri? = null
    private var bitmap: Bitmap? = null

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

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 3, 299, 299), DataType.FLOAT32)
        inputFeature0.loadBuffer(inputBuffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val confidenceArray = outputFeature0.floatArray

        // Daftar label
        val labels = arrayOf("Calculus", "Caries", "Gingivitis", "Ulcer", "Tooth Discoloration", "Hypodontia")

        val maxIndex = confidenceArray.indices.maxByOrNull { confidenceArray[it] } ?: -1
        val result = if (maxIndex >= 0) labels[maxIndex] else "Undetected wound"


//        Toast.makeText(this, "Prediction: $result", Toast.LENGTH_LONG).show()

//        Log.d("Prediction Result", "Predicted label: $result")
        for (i in confidenceArray.indices) {
            Log.d("Confidence Score", "Label: ${labels[i]}, Confidence: ${confidenceArray[i]}")
        }

        val intent = Intent(this, PredictActivity::class.java)
        intent.putExtra("prediction_result", result)

        currentImageUri?.let {
            intent.putExtra("image_uri", it.toString())
        }

        startActivity(intent)

        model.close()
        binding.progressIndicator.visibility = View.GONE
    }

}
