package com.zeepy.zeepyforandroid.review.data.entity

import androidx.annotation.StringRes

data class LessorPersonalityModel(
    val emoji: Int,
    @StringRes val personality: Int,
    var isSelected: Boolean = false
)