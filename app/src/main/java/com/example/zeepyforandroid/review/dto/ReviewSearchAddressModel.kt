package com.example.zeepyforandroid.review.dto

data class ReviewSearchAddressModel(
    val buildingName: String,
    val buildingPicture: String?,
    val personalityReview: String,
    val buildingReview: String,
    val buildingType: String,
    val roomType: String,
    val floor: String
)