package com.zeepy.zeepyforandroid.map.datasource

import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO

interface RemoteBuildingsDataSource {
    suspend fun getBuildingsInfoByLocation(latitudeGreater: Double, latitudeLess: Double, longitudeGreater: Double, longitudeLess: Double): List<ResponseBuildingInfoDTO>
}