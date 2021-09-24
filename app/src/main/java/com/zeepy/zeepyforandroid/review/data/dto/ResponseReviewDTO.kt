package com.zeepy.zeepyforandroid.review.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseReviewDTO(
    val communcationTendency: String,
    val furnitures: List<String>,
    val id: Int,
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
    val user: User,
    val waterPressure: String
): Parcelable