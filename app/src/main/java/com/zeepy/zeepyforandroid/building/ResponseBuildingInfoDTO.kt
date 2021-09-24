package com.zeepy.zeepyforandroid.building

import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO

data class ResponseBuildingInfoDTO(
    val apartmentName: String,
    val areaCode: Int,
    val buildYear: Int,
    val buildingDeals: List<BuildingDealDTO>,
    val buildingLikes: List<BuildingLikeDTO>,
    val buildingType: String,
    val exclusivePrivateArea: Double,
    val fullNumberAddress: String,
    val fullRoadNameAddress: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val reviews: List<ResponseReviewDTO>,
    val shortAddress: String,
    val shortNumberAddress: String,
    val shortRoadNameAddress: String
)