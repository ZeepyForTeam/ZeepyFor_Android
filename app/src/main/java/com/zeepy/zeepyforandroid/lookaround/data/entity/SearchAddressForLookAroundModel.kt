package com.zeepy.zeepyforandroid.lookaround.data.entity

data class SearchAddressForLookAroundModel(
    val apartmentName: String,
    val fullNumberAddress: String,
    val fullRoadNameAddress: String,
    val id: Int,
    val cityDistinct: String,
    val primaryAddress: String,
    val shortRoadNameAddress: String
)
