package com.example.oraldiseasesapp.articles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticlesRepository(private val apiKey: String) {
    private val articlesService: ArticlesService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        articlesService = retrofit.create(ArticlesService::class.java)
    }

    fun getTopHeadlines(): LiveData<List<ArticlesData>> {
        val data = MutableLiveData<List<ArticlesData>>()
        articlesService.getTopHeadlines(apiKey = apiKey).enqueue(object :
            Callback<ArticlesResponse> {
            override fun onResponse(call: Call<ArticlesResponse>, response: Response<ArticlesResponse>) {
                if (response.isSuccessful) {
                    data.value = response.body()?.articles
                }
            }
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.d("ArticlesRepository", "Failed to get top headlines", t)
            }
        })
        return data
    }
}

class ArticlesViewModel(apiKey: String) : ViewModel() {
    private val repository = ArticlesRepository(apiKey)
    val articles = repository.getTopHeadlines()
}
