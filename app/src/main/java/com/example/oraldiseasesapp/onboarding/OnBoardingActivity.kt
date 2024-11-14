package com.example.oraldiseasesapp.onboarding

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.oraldiseasesapp.R
import com.example.oraldiseasesapp.databinding.ActivityOnBoardingBinding
import com.example.oraldiseasesapp.video.ListVideoActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var onboardingAdapter: OnBoardingPagerAdapter
    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onboardingAdapter = OnBoardingPagerAdapter(
            listOf(
                OnBoardingItem(
                    R.raw.animation1,
                    "Welcome",
                    "Welcome to Oral Disease App, This app is designed to help you maintain your oral health easily. Let's get started!"
                ),
                OnBoardingItem(
                    R.raw.animation2,
                    "Get to Know the Features",
                    "Take a photo of your mouth for a quick oral health check. Get daily oral health tips."
                ),
                OnBoardingItem(
                    R.raw.animation3,
                    "Enjoy The App!",
                    "Ready to take care of your oral health? Start by selecting the features you want to use. Feel free to explore the app further!"
                )
            )
        )
        binding.onboardingViewPager.adapter = onboardingAdapter
        setCurrentOnboardingIndicators(0)
        setOnboadingIndicator()

        binding.onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentOnboardingIndicators(position)
            }
        })

        binding.buttonOnBoardingAction.setOnClickListener{
            if (binding.onboardingViewPager.currentItem + 1 < onboardingAdapter.itemCount) {
                binding.onboardingViewPager.currentItem += binding.onboardingViewPager.currentItem
            } else {
                val intent = Intent(this, ListVideoActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun setOnboadingIndicator() {
        val indicators = arrayOfNulls<ImageView>(onboardingAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, R.drawable.dot_inactive
                ))
            indicators[i]?.layoutParams = layoutParams
            binding.layoutOnboardingIndicators.addView(indicators[i])
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCurrentOnboardingIndicators(index: Int) {
        val childCount = binding.layoutOnboardingIndicators.childCount
        for (i in 0 until childCount) {
            val imageView = binding.layoutOnboardingIndicators.getChildAt(i) as ImageView
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dot_active))
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dot_inactive))
            }
        }
        if (index == onboardingAdapter.itemCount - 1) {
            binding.buttonOnBoardingAction.text = "Get Started"
            binding.buttonOnBoardingAction.visibility = View.VISIBLE
        } else {
            binding.buttonOnBoardingAction.visibility = View.GONE
        }
    }
}