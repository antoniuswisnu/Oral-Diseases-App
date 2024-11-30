package com.example.oraldiseasesapp.news

import android.os.Parcelable
import com.example.oraldiseasesapp.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import kotlinx.parcelize.Parcelize

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("q") country: String = "medical",
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): Response<NewsResponse>
}

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

@Parcelize
data class Article(
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
) : Parcelable