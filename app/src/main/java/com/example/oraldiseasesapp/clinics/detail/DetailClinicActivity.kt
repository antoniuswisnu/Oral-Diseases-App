package com.example.oraldiseasesapp.clinics.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.oraldiseasesapp.R
import com.example.oraldiseasesapp.databinding.ActivityDetailClinicBinding
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.random.Random

class DetailClinicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailClinicBinding
    private val TAG = "ClinicDetailActivity"

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailClinicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        latitude = intent.getDoubleExtra("LATITUDE", 0.0)
        longitude = intent.getDoubleExtra("LONGITUDE", 0.0)
        val placeId = intent.getStringExtra("PLACE_ID")
        val name = intent.getStringExtra("CLINIC_NAME")
        val address = intent.getStringExtra("CLINIC_ADDRESS")

        setRandomClinicPhoto()

        binding.tvClinicName.text = name
        binding.tvClinicAddress.text = address

        placeId?.let {
            fetchClinicDetails(it)
        }

        binding.btnNavigate.setOnClickListener {
            openInGoogleMaps()
        }

        binding.btnContact.setOnClickListener {
            val phoneNumber = binding.tvPhoneNumber.text.toString()

            if (phoneNumber.isNotEmpty() && phoneNumber != "Tidak tersedia") {
                val callIntent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:$phoneNumber")
                }
                startActivity(callIntent)
            } else {
                Log.e(TAG, "Nomor telepon tidak tersedia.")
            }
        }
    }

    private fun setRandomClinicPhoto() {
        val photos = listOf(
            R.drawable.clinic1,
            R.drawable.clinic3,
            R.drawable.clinic4,
            R.drawable.clinic6,
        )

        val randomPhotoId = photos[Random.nextInt(photos.size)]

        binding.ivClinicPhoto.setImageResource(randomPhotoId)
    }

    private fun fetchClinicDetails(placeId: String) {
        val apiKey = "AIzaSyDWvTCGCS5ChiGue-zyl5CNnp_ospsfv_M"
        val url = "https://maps.googleapis.com/maps/api/place/details/json?place_id=$placeId&key=$apiKey"

        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Error fetching clinic details: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseData = responseBody.string()
                    try {
                        val jsonObject = JSONObject(responseData)
                        val result = jsonObject.getJSONObject("result")

                        val geometry = result.optJSONObject("geometry")
                        val location = geometry?.optJSONObject("location")
                        val lat = location?.optDouble("lat", 0.0) ?: 0.0
                        val lng = location?.optDouble("lng", 0.0) ?: 0.0

                        if (lat != 0.0 && lng != 0.0) {
                            latitude = lat
                            longitude = lng
                        }

                        val photoReference = result.optJSONArray("photos")?.getJSONObject(0)?.getString("photo_reference")
                        val phoneNumber = result.optString("formatted_phone_number", "Tidak tersedia")
                        val website = result.optString("website", "Tidak tersedia")
                        val openingHours = result.optJSONObject("opening_hours")
                        val weekdayText = openingHours?.optJSONArray("weekday_text")
                        val rating = result.optDouble("rating", 0.0)

                        val hoursText = StringBuilder()
                        weekdayText?.let {
                            for (i in 0 until it.length()) {
                                hoursText.append(it.getString(i)).append("\n")
                            }
                        }

                        runOnUiThread {
                            photoReference?.let {
                                val photoUrl = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=$it&key=$apiKey"
                                Glide.with(this@DetailClinicActivity)
                                    .load(photoUrl)
                                    .into(binding.ivClinicPhoto)
                            }

                            binding.tvPhoneNumber.text = phoneNumber
                            binding.tvWebsite.text = website
                            binding.tvClinicRating.text = "Rating: $rating"
                            binding.tvClinicHours.text = hoursText.toString()

                            if (website.isNotEmpty()) {
                                binding.tvWebsite.apply {
                                    text = website
                                    setOnClickListener {
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(website))
                                        startActivity(intent)
                                    }
                                    visibility = android.view.View.VISIBLE
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing clinic details: ${e.message}")
                    }
                }
            }
        })
    }

    private fun openInGoogleMaps() {
        if (latitude != 0.0 && longitude != 0.0) {
            val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                Log.e(TAG, "Google Maps tidak ditemukan.")
            }
        } else {
            Log.e(TAG, "Koordinat tidak tersedia.")
        }
    }

}