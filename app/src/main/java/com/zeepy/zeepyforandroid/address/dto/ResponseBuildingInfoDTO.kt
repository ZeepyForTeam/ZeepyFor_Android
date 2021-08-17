package com.zeepy.zeepyforandroid.address.dto

import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO

data class ResponseBuildingInfoDTO(
    val apartmentName: String,
    val areaCode: Int,
    val buildYear: Int,
    val buildingDeals: List<BuildingDeals>,
    val buildingLikes: List<BuildingLikes>,
    val exclusivePrivateArea: Int,
    val fullNumberAddress: String,
    val fullRoadNameAddress: String,
    val id: Int,
    val latitude: Int,
    val longitude: Int,
    val reviews: List<ResponseReviewDTO>,
    val shortAddress: String,
    val shortNumbertAddress: String,
    val shortRoadNameAddress: String
) {
    data class BuildingDeals(
        val dealCost: Int,
        val dealDate: DealDate,
        val dealType: String,
        val deposit: Int,
        val floor: Int,
        val id: Int,
        val mohthlyRent: Int
    ) {
        data class DealDate(
            val date: Int,
            val day: Int,
            val hours: Int,
            val minutes: Int,
            val month: Int,
            val nanos: Int,
            val seconds: Int,
            val time: Int,
            val timezoneOffset: Int,
            val year: Int
        )
    }

    data class BuildingLikes(
        val email: String,
        val id: Int,
        val likeDate: String
    )
}