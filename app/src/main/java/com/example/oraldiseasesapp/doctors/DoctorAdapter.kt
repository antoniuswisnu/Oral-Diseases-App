package com.example.oraldiseasesapp.doctors

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oraldiseasesapp.databinding.ItemDoctorsBinding

class DoctorAdapter(private val doctorList: List<Doctor>) : RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    inner class DoctorViewHolder(val binding: ItemDoctorsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val binding = ItemDoctorsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DoctorViewHolder(binding)
    }

    @SuppressLint("QueryPermissionsNeeded")
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
                val phoneNumber = "62895326509445"
                val message = "Halo, Apakah anda mempunyai waktu luang? saya ingin berkonsultasi mengenai kesehatan gigi dan mulut saya"

                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("http://api.whatsapp.com/send?phone=$phoneNumber&text=$message")
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = doctorList.size
}