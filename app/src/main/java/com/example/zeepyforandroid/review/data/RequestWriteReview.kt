package com.example.zeepyforandroid.review.data

import androidx.annotation.StringRes
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.application.ZeepyApplication
import java.lang.IllegalArgumentException

data class RequestWriteReview(
    val address: String,
    val buildingId: Int,
    val communcationTendency: String,
    val furnitures: List<String>,
    val imageUrls: List<String>,
    val lessorAge: String,
    val lessorGender: String,
    val lessorReview: String,
    val lightning: String,
    val pest: String,
    val review: String,
    val roomCount: String,
    val soundInsulation: String,
    val totalEvaluation: String,
    val user: Int,
    val waterPressure: String
) {



}