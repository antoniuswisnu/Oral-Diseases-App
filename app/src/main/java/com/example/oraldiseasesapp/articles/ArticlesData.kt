package com.example.oraldiseasesapp.articles

data class ArticlesResponse(val articles: List<ArticlesData>)
data class ArticlesData(
    val title: String,
    val description: String?,
    val content: String?,
    val urlToImage: String?,
    val url: String
)
