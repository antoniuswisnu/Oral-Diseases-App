package com.example.oraldiseasesapp.articles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oraldiseasesapp.BuildConfig
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.databinding.ActivityListArticlesBinding

class ListArticlesActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapter: ArticlesAdapter
    private lateinit var binding: ActivityListArticlesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ArticlesViewModel(BuildConfig.NEWS_API_KEY)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        adapter = ArticlesAdapter(emptyList()) {
            val intent = Intent(this, ArticlesActivity::class.java)
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter

        viewModel.articles.observe(this) { articles ->
            adapter.updateArticles(articles)
        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        Log.d("Articles", viewModel.toString())
        Log.d("Articles", viewModel.articles.toString())
        Log.d("Articles", viewModel.articles.value.toString())
    }
}