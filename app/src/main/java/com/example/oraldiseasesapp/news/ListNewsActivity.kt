package com.example.oraldiseasesapp.news

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.databinding.ActivityListNewsBinding
import kotlinx.coroutines.launch

class ListNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            val repository = NewsRepository()
            val articles = repository.getTopHeadlines()

            val adapter = NewsAdapter(articles, object : NewsItemClickListener {
                override fun onItemClick(article: Article) {
                    val intent = Intent(this@ListNewsActivity, NewsDetailActivity::class.java)
                    intent.putExtra(NewsDetailActivity.EXTRA_ARTICLE, article)
                    startActivity(intent)
                }
            })
            binding.recyclerView.adapter = adapter

        }

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}