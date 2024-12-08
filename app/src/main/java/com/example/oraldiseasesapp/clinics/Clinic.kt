package com.example.oraldiseasesapp.clinics

data class Clinic(
    val name: String,
    val rating: Double,
    val userRatingsTotal: Int,
    val vicinity: String,
    val openingStatus: String,
    val placeId: String
)