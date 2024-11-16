package com.example.oraldiseasesapp.articles

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticlesService {
    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("q") query: String = "oral",
        @Query("apiKey") apiKey: String
    ): Call<ArticlesResponse>
}
