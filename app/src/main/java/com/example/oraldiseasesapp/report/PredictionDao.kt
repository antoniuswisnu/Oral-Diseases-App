package com.example.oraldiseasesapp.report

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PredictionDao {
    @Insert
    suspend fun insert(prediction: Prediction)

    @Query("SELECT * FROM predictions ORDER BY id DESC")
    fun getAllPredictions(): Flow<List<Prediction>>

    @Query("SELECT * FROM predictions WHERE id = :id")
    suspend fun getPredictionById(id: Int): Prediction

}