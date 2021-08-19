package com.zeepy.zeepyforandroid.building

data class BuildingDealDTO(
    val dealCost: Int,
    val dealDate: String,
    val dealType: String,
    val deposit: Int,
    val floor: Int,
    val id: Int,
    val monthlyRent: Int
)