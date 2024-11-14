package com.example.oraldiseasesapp.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oraldiseasesapp.R

class ListVideoAdapter(
    private val videoList: List<ItemsVideoViewModel>,
    private val onItemClick: (ItemsVideoViewModel) -> Unit
) : RecyclerView.Adapter<ListVideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoItem = videoList[position]
        holder.tvTitle.text = videoItem.title
        holder.tvDesc.text = videoItem.desc
        holder.itemView.setOnClickListener { onItemClick(videoItem) }
    }

    override fun getItemCount(): Int = videoList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
    }
}
