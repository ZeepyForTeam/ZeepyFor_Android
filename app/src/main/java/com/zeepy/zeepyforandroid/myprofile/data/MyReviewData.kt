package com.zeepy.zeepyforandroid.myprofile.data

data class MyReviewData(
    val buildingName: String?,
    val dateTime: String,
    val lessorReview: String?,
    val lessorCommunicationStyle: String?,
    val houseReview: List<HouseReview>
)

data class HouseReview(
    val soundproof: String,
    val insects: String,
    val sunlight: String,
    val waterPressure: String
)
