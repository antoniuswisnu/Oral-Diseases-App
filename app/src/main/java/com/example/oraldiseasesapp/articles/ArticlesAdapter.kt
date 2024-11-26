package com.example.oraldiseasesapp.articles

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oraldiseasesapp.R

class ArticlesAdapter(private var articles: List<ArticlesData>, private val onItemClicked: (ArticlesData) -> Unit) :
    RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder>() {

    inner class ArticlesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById<TextView>(R.id.newsTitle)
        val description: TextView = view.findViewById<TextView>(R.id.newsDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_articles, parent, false)
        return ArticlesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val article = articles[position]
        holder.title.text = article.title
        holder.description.text = article.description
        holder.itemView.setOnClickListener { onItemClicked(article) }

        Log.d("test", article.toString())
        Log.d("test", article.title)
    }

    override fun getItemCount() = articles.size


    fun updateArticles(newArticles: List<ArticlesData>) {
        articles = newArticles
        notifyDataSetChanged()
    }
}
