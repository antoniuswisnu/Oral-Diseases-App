package com.example.oraldiseasesapp.onboarding

import android.support.annotation.RawRes

data class OnBoardingItem(
    @RawRes val image: Int,
    val title: String,
    val description: String
)
