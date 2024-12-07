package com.example.oraldiseasesapp.clinics

import ClinicAdapter
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oraldiseasesapp.R
import com.example.oraldiseasesapp.clinics.detail.DetailClinicActivity
import com.example.oraldiseasesapp.databinding.ActivityClinicBinding
import com.google.android.gms.location.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import kotlin.random.Random

class ClinicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityClinicBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var clinicAdapter: ClinicAdapter
    private val clinicList = mutableListOf<Clinic>()
    private val TAG = "ClinicActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityClinicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        clinicAdapter = ClinicAdapter(clinicList)
        binding.recyclerViewClinics.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewClinics.adapter = clinicAdapter

        clinicAdapter.onItemClick = { clinic ->
            val intent = Intent(this, DetailClinicActivity::class.java)
            intent.putExtra("PLACE_ID", clinic.placeId)
            intent.putExtra("CLINIC_NAME", clinic.name)
            intent.putExtra("CLINIC_ADDRESS", clinic.vicinity)
            startActivity(intent)
        }


        getCurrentLocation()
    }


    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }

        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val lat = location.latitude
                    val lng = location.longitude
                    Log.d(TAG, "Lokasi saat ini: $lat, $lng")
                    fetchNearbyClinics(lat, lng)
                } else {
                    Log.d(TAG, "Lokasi terakhir tidak tersedia, meminta lokasi terkini...")
                    requestLocationUpdates()
                }
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Error: Izin lokasi tidak diberikan atau dicabut. ${e.message}")
        }
    }

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(TAG, "Izin lokasi tidak diberikan.")
            return
        }

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000 // 10 detik
            fastestInterval = 5000 // 5 detik
        }

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    val lat = location.latitude
                    val lng = location.longitude
                    Log.d(TAG, "Lokasi diperbarui: $lat, $lng")

                    // Setelah lokasi diperbarui, kirim data lokasi ke DetailClinicActivity
                    val intent = Intent(this@ClinicActivity, DetailClinicActivity::class.java)
                    intent.putExtra("LATITUDE", lat)  // Latitude yang diperbarui
                    intent.putExtra("LONGITUDE", lng)  // Longitude yang diperbarui
                    startActivity(intent)

                    // Memanggil fetchNearbyClinics (jika perlu)
                    fetchNearbyClinics(lat, lng)
                }

                // Menghentikan pembaruan lokasi setelah mendapatkan satu lokasi
                fusedLocationClient.removeLocationUpdates(this)
            }
        }


        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, mainLooper)
        } catch (e: SecurityException) {
            Log.e(TAG, "Error: ${e.message}")
        }
    }

    private fun fetchNearbyClinics(latitude: Double, longitude: Double) {
        val apiKey = "AIzaSyDWvTCGCS5ChiGue-zyl5CNnp_ospsfv_M"
        val radius = 10000
        val type = "dentist"
        val url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                "?location=$latitude,$longitude&radius=$radius&type=$type&key=$apiKey"

        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "Error fetching clinics: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let { responseBody ->
                    val responseData = responseBody.string()
                    try {
                        val jsonObject = JSONObject(responseData)
                        val results: JSONArray = jsonObject.getJSONArray("results")
                        clinicList.clear()

                        for (i in 0 until results.length()) {
                            val place = results.getJSONObject(i)
                            val name = place.getString("name")
                            val rating = place.optDouble("rating", 0.0)
                            val userRatingsTotal = place.optInt("user_ratings_total", 0)
                            val vicinity = place.optString("vicinity", "Alamat tidak tersedia")
                            val placeId = place.getString("place_id")

                            val openingHours = place.optJSONObject("opening_hours")
                            val isOpenNow = openingHours?.optBoolean("open_now", false) ?: false
                            val openingStatus = if (isOpenNow) "Buka" else "Tutup"

                            clinicList.add(Clinic(name, rating, userRatingsTotal, vicinity, openingStatus, placeId))
                        }

                        runOnUiThread {
                            clinicAdapter.notifyDataSetChanged()
                        }

                    } catch (e: Exception) {
                        Log.e(TAG, "Error parsing response: ${e.message}")
                    }
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation()
        } else {
            Log.e(TAG, "Izin lokasi ditolak.")
        }
    }
}
