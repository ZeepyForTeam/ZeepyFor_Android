package com.zeepy.zeepyforandroid.review.data.dto

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
    val waterPressure: String,
)