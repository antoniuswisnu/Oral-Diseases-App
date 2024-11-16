package com.example.oraldiseasesapp.articles

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.oraldiseasesapp.databinding.ActivityArticlesBinding

class ArticlesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticlesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article: ArticlesData = intent.getSerializableExtra("article") as ArticlesData
//        val article = intent.getParcelableExtra<ArticlesData>("article")
        binding.articlesDetailTitle.text = article.title
        binding.articlesDetailContent.text = article.content
    }
}
