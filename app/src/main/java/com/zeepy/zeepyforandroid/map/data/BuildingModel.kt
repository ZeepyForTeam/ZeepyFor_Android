package com.zeepy.zeepyforandroid.map.data

import com.zeepy.zeepyforandroid.building.BuildingDealDTO
import com.zeepy.zeepyforandroid.building.BuildingLikeDTO
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO

// FIXME: Add the remaining necessary fields
data class BuildingModel(
    val apartmentName: String,
    val areaCode: Int,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val shortAddress: String,
)
