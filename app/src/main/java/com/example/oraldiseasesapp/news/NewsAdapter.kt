package com.example.oraldiseasesapp.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oraldiseasesapp.R

interface NewsItemClickListener {
    fun onItemClick(article: Article)
}

class NewsAdapter(private var articles: List<Article>, private val clickListener: NewsItemClickListener) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var article = articles[position]

        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description
        Glide.with(holder.itemView)
            .load(article.urlToImage)
            .into(holder.imageView)

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(article)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}