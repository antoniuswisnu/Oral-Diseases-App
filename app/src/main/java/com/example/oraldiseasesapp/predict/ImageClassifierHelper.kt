package com.example.oraldiseasesapp.predict

import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import android.util.Log
import android.view.Surface
import androidx.camera.core.ImageProxy
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.gpu.CompatibilityList
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.Classifications
import org.tensorflow.lite.task.vision.classifier.ImageClassifier
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import org.tensorflow.lite.support.image.ImageProcessor

class ImageClassifierHelper(
    var threshold: Float = 0.1f,
    var maxResults: Int = 7,
    var numThreads: Int = 4,
    var currentDelegate: Int = DELEGATE_CPU,
    val modelName: String = "mobilenet_v1_1.0_224_quantized_1_metadata_1.tflite",
    val context: Context,
    val imageClassifierListener: ClassifierListener?
) {
    private var imageClassifier: ImageClassifier? = null

    init {
        setupImageClassifier()
    }

    fun clearImageClassifier() {
        //use if you change the threshold, maxResult, threads, or delegates.
        imageClassifier?.close()
        imageClassifier = null
    }

    fun isClosed(): Boolean {
        return imageClassifier == null
    }

    fun setupImageClassifier() {
        val optionsBuilder = ImageClassifier.ImageClassifierOptions.builder()
            .setScoreThreshold(threshold)
            .setMaxResults(maxResults)

        val baseOptionsBuilder = BaseOptions.builder().setNumThreads(numThreads)

        when (currentDelegate) {
            DELEGATE_CPU -> {
                // Default
            }

            DELEGATE_GPU -> {
                if (CompatibilityList().isDelegateSupportedOnThisDevice) {
                    baseOptionsBuilder.useGpu()
                } else {
                    imageClassifierListener?.onError("GPU is not supported on this device")
                }
            }

            DELEGATE_NNAPI -> {
                baseOptionsBuilder.useNnapi()
            }
        }

        optionsBuilder.setBaseOptions(baseOptionsBuilder.build())

        try {
            imageClassifier =
                ImageClassifier.createFromFileAndOptions(context, modelName, optionsBuilder.build())
        } catch (e: IllegalStateException) {
            imageClassifierListener?.onError(
                "Image classifier failed to initialize. See error logs for details"
            )
            Log.e(TAG, "TFLite failed to load model with error: " + e.message)
        }
    }

    fun classify(image: ImageProxy) {
        if (imageClassifier == null) {
            setupImageClassifier()
        }

        val bitmapBuffer = Bitmap.createBitmap(
            image.width,
            image.height,
            Bitmap.Config.ARGB_8888
        )

        image.use { bitmapBuffer.copyPixelsFromBuffer(image.planes[0].buffer) }
        image.close()

        // Inference time is the difference between the system time at the start and finish of the
        // process
        var inferenceTime = SystemClock.uptimeMillis()

        // Create preprocessor for the image.
        // See https://www.tensorflow.org/lite/inference_with_metadata/
        //            lite_support#imageprocessor_architecture
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR))
//            .add(NormalizeOp(0.0f, 1.0f)) // Normalize the image to [0, 1]
//            .add(ResizeOp(1,299,299,3)
            .build()

        // Preprocess the image and convert it into a TensorImage for classification.
        val tensorImage = imageProcessor.process(TensorImage.fromBitmap(bitmapBuffer))

        val imageProcessingOptions = ImageProcessingOptions.builder()
            .setOrientation(getOrientationFromRotation(image.imageInfo.rotationDegrees))
            .build()

        val results = imageClassifier?.classify(tensorImage, imageProcessingOptions)
        inferenceTime = SystemClock.uptimeMillis() - inferenceTime
        imageClassifierListener?.onResults(
            results,
            inferenceTime
        )
    }

    // Receive the device rotation (Surface.x values range from 0->3) and return EXIF orientation
    // http://jpegclub.org/exif_orientation.html
    private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation {
        return when (rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }
    }

    interface ClassifierListener {
        fun onError(error: String)
        fun onResults(
            results: List<Classifications>?,
            inferenceTime: Long
        )
    }

    companion object {
        const val DELEGATE_CPU = 0
        const val DELEGATE_GPU = 1
        const val DELEGATE_NNAPI = 2

        private const val TAG = "ImageClassifierHelper"
    }
}























//class ImageClassifierHelper(
//    private val threshold: Float = 0.1f,
//    private val maxResults: Int = 7,
//    private val numThreads: Int = 4,
//    private val modelName: String,
//    private val context: Context,
//    private val imageClassifierListener: ClassifierListener?
//) {
//    private var tflite: Interpreter? = null
//
//    init {
//        setupInterpreter()
//    }
//
//    private fun setupInterpreter() {
//        try {
//            val options = Interpreter.Options().apply { numThreads = numThreads }
//            val modelFile = context.assets.openFd(modelName).use {
//                FileInputStream(it.fileDescriptor).channel.map(
//                    FileChannel.MapMode.READ_ONLY,
//                    it.startOffset,
//                    it.declaredLength
//                )
//            }
//            tflite = Interpreter(modelFile, options)
//        } catch (e: Exception) {
//            imageClassifierListener?.onError("Error loading model: ${e.message}")
//        }
//    }
//
//    fun classify(bitmap: Bitmap) {
//        val input = preprocessImage(bitmap)
//        val output = Array(1) { FloatArray(maxResults) }
//        tflite?.run(input, output)
//
//        val results = output[0]
//            .mapIndexed { index, confidence ->
//                ClassifierResult(label = "Label $index", confidence = confidence)
//            }
//            .filter { it.confidence >= threshold }
//            .sortedByDescending { it.confidence }
//            .take(maxResults)
//
//        Log.d("PreviewActivity", "Results: $results")
//
//        imageClassifierListener?.onResults(results, System.currentTimeMillis())
//    }
//
//    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
//        val resized = Bitmap.createScaledBitmap(bitmap, 299, 299, true)
//        val buffer = ByteBuffer.allocateDirect(4 * 1 * 299 * 299 * 3).apply { order(ByteOrder.nativeOrder()) }
//        val pixels = IntArray(299 * 299)
//        resized.getPixels(pixels, 0, 299, 0, 0, 299, 299)
//
//        for (pixel in pixels) {
//            val r = ((pixel shr 16) and 0xFF) / 255.0f
//            val g = ((pixel shr 8) and 0xFF) / 255.0f
//            val b = (pixel and 0xFF) / 255.0f
//
//            buffer.putFloat(r)
//            buffer.putFloat(g)
//            buffer.putFloat(b)
//        }
//
//        return buffer
//        Log.d("PreviewActivity", "Bitmap Dimensions: ${resized.width}x${resized.height}")
//        Log.d("PreviewActivity", "Stride (should be 299): ${299}")
//    }
//
//
//    interface ClassifierListener {
//        fun onResults(results: List<ClassifierResult>, inferenceTime: Long)
//        fun onError(error: String)
//    }
//
//    data class ClassifierResult(val label: String, val confidence: Float)
//}
