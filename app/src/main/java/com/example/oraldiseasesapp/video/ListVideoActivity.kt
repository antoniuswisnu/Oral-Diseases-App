package com.example.oraldiseasesapp.video

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oraldiseasesapp.MainActivity
import com.example.oraldiseasesapp.databinding.ActivityListVideoBinding

class ListVideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.rvVideo.layoutManager = LinearLayoutManager(this)
        val adapter = ListVideoAdapter(VideoData.videoList) { videoItem ->
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("VIDEO_ID", videoItem.videoId)
            intent.putExtra("TITLE", videoItem.title)
            intent.putExtra("DESC", videoItem.desc)
            startActivity(intent)
        }
        binding.rvVideo.adapter = adapter
    }
}
