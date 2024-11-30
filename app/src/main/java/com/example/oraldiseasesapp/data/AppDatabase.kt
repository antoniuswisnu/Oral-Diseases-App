package com.example.oraldiseasesapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.oraldiseasesapp.report.Prediction
import com.example.oraldiseasesapp.report.PredictionDao

@Database(entities = [Prediction::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun predictionDao(): PredictionDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "predictions_db"
                ).build().also { instance = it }
            }
        }
    }
}