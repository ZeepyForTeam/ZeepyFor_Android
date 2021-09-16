package com.zeepy.zeepyforandroid.map.data

import com.zeepy.zeepyforandroid.building.BuildingDealDTO
import com.zeepy.zeepyforandroid.building.BuildingLikeDTO
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO

// FIXME: Add the remaining necessary fields
data class BuildingModel(
    val id: Int,
    val buildYear: Int?,
    val apartmentName: String?,
    val shortAddress: String?,
    val fullNumberAddress: String,
    val fullRoadNameAddress: String,
    val shortRoadNameAddress: String?,
    val areaCode: Int,
    val latitude: Double,
    val longitude: Double,
    val reviews: List<ResponseReviewDTO>
)
