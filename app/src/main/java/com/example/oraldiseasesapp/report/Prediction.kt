package com.example.oraldiseasesapp.report

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Predictions")
data class Prediction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    val confidence: Float,
    val description: String,
    val imageUri: String?
)
