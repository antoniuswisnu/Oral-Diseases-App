package com.example.oraldiseasesapp.doctors

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.oraldiseasesapp.databinding.ItemDoctorsBinding

class DoctorAdapter(private val doctorList: List<Doctor>) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(val binding: ItemDoctorsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ItemDoctorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctorList[position]
        with(holder.binding) {
            titleDoc.text = doctor.name
            spesialis.text = doctor.specialty
            price.text = doctor.price
            tvPengalaman.text = doctor.experience
            tvRating.text = doctor.rating
            profileDoctor.setImageResource(doctor.imageResId)
            btnChat.setOnClickListener {
                Toast.makeText(holder.binding.root.context, "Coming soon", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int = doctorList.size
}