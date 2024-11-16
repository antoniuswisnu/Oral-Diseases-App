package com.example.oraldiseasesapp.tootpaste

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.databinding.ActivityToothpasteBinding

class ToothpasteActivity : AppCompatActivity(), OnItemSelectedListener {
    var toothpaste = arrayOf<String?>("Caries", "ulcers",
        "hypodontia", "Gingivitis",
        "calculus", "toothDiscoloration")

    private lateinit var binding: ActivityToothpasteBinding
    private var isFirstSelection = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToothpasteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toothpasteNames = ToothpasteData.toothpasteList.map { it.name }

        val ad = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            toothpasteNames
        )
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = ad

        binding.spinner.onItemSelectedListener = this

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?,
                                view: View, position: Int,
                                id: Long) {
        if (isFirstSelection) {
            isFirstSelection = false
            return
        }

        val selectedToothpaste = ToothpasteData.toothpasteList[position]

        val intent = Intent(this, DetailToothPasteActivity::class.java).apply {
            putExtra("name", selectedToothpaste.name)
            putExtra("description", selectedToothpaste.description)
            putExtra("image", selectedToothpaste.image)
        }
        startActivity(intent)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}
