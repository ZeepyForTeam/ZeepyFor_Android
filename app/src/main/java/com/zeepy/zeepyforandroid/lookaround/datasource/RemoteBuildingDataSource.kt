package com.zeepy.zeepyforandroid.lookaround.datasource

import com.zeepy.zeepyforandroid.building.BuildingLikeRequestDTO
import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO

interface RemoteBuildingDataSource {
    suspend fun getBuildingInfoById(id: Int): ResponseBuildingInfoDTO?
    suspend fun scrapBuilding(buildingLikeRequestDTO: BuildingLikeRequestDTO): Unit?
    suspend fun cancelScrapBuilding(id: Int): Unit?
}