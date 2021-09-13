package com.zeepy.zeepyforandroid.map.repository

import com.zeepy.zeepyforandroid.map.data.BuildingModel

interface BuildingsRepository {
    suspend fun getBuildingsInfoByLocation(latitudeGreater: Double, latitudeLess: Double, longitudeGreater: Double, longitudeLess: Double): List<BuildingModel>
    suspend fun getBuildingsAll(): List<BuildingModel>
}