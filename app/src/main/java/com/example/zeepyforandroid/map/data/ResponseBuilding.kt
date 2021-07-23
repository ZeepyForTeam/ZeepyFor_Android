package com.example.zeepyforandroid.map.data

data class ResponseBuilding(
    val status: Int,
    val message: String,
    val data: List<Building>
)

data class Building(
    val id: Int,
    val buildingName: String,
    val buildingAddress: String,
    val latitude: Double,
    val longitude: Double
)
