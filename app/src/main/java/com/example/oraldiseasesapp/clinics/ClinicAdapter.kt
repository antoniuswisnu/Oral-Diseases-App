package com.example.oraldiseasesapp.clinics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oraldiseasesapp.R
import com.example.oraldiseasesapp.clinics.Clinic
import com.example.oraldiseasesapp.databinding.ItemClinicsBinding
import kotlin.random.Random

class ClinicAdapter(private val clinics: List<Clinic>) : RecyclerView.Adapter<ClinicAdapter.ClinicViewHolder>() {

    var onItemClick: ((Clinic) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {
        val binding = ItemClinicsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClinicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClinicViewHolder, position: Int) {
        val clinic = clinics[position]
        holder.bind(clinic)
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(clinic)
        }
    }

    override fun getItemCount(): Int = clinics.size

    class ClinicViewHolder(private val binding: ItemClinicsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clinic: Clinic) {
            binding.tvClinicName.text = clinic.name
            binding.tvRating.text = clinic.rating.toString()
            binding.tvUserRatingsTotal.text = clinic.userRatingsTotal.toString()
            binding.tvVicinity.text = clinic.vicinity
            binding.tvOpeningStatus.text = clinic.openingStatus

            val photos = listOf(
                R.drawable.clinic1,
                R.drawable.clinic3,
                R.drawable.clinic4,
                R.drawable.clinic6
            )
            val randomPhotoId = photos[Random.nextInt(photos.size)]
            binding.hostpitalPict.setImageResource(randomPhotoId)
        }
    }



}