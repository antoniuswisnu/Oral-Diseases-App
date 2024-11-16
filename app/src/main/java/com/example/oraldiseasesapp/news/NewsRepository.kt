package com.example.oraldiseasesapp.news
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService: NewsApiService = retrofit.create(NewsApiService::class.java)

    suspend fun getTopHeadlines(): List<Article> {
        val response = apiService.getTopHeadlines()
        if (response.isSuccessful) {
            Log.d("NewsRepository", "getTopHeadlines: ${response.body()?.articles} ${response.body()} ${response.body()?.status} ${response.body()?.totalResults} ${response.body()?.articles?.size}")
            return response.body()?.articles ?: emptyList()
        } else {
            Log.d("NewsRepository", "getTopHeadlines: ${response.code()} ${response.message()}")
            throw ApiException(response.code(), response.message())
        }
    }
}

class ApiException(code: Int, message: String) : Exception("API request failed with code $code: $message")



