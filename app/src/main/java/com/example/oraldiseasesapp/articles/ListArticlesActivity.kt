package com.example.oraldiseasesapp.articles

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.oraldiseasesapp.BuildConfig
import com.example.oraldiseasesapp.R
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
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        viewModel.articles.observe(this) { articles ->
            adapter = ArticlesAdapter(articles) { article ->
                val intent = Intent(this, ArticlesActivity::class.java)
                intent.putExtra("article", article)
                startActivity(intent)
            }
            recyclerView.adapter = adapter
        }
    }
}
