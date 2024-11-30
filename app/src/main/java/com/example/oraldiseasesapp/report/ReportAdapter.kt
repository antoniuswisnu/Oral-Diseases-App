package com.example.oraldiseasesapp.report

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.oraldiseasesapp.databinding.ItemReportBinding

class ReportAdapter(private var predictions: List<Prediction>) :
    RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val prediction = predictions[position]
        with(holder.binding) {
            tvLabel.text = prediction.label
            tvConfidence.text = "${prediction.confidence}"
            tvDescription.text = prediction.description
            prediction.imageUri?.let { imageUri ->
                try {
                    val inputStream = holder.itemView.context.contentResolver.openInputStream(Uri.parse(imageUri))
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    ivPreview.setImageBitmap(bitmap)
                    inputStream?.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("ReportAdapter", "Error loading image URI: $imageUri", e)
                }
            }
            Log.d("ReportAdapter", "Binding data for position $position: $prediction")
            Log.d("ReportAdapter", "Image URI: ${prediction.imageUri}")
        }
    }

    fun updateData(newReports: List<Prediction>) {
        val diffCallback = ReportDiffCallback(predictions, newReports)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        predictions = newReports
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = predictions.size
}

class ReportDiffCallback(
    private val oldList: List<Prediction>,
    private val newList: List<Prediction>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}

