package com.zeepy.zeepyforandroid.lookaround.repository

import com.zeepy.zeepyforandroid.building.BuildingLikeRequestDTO
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel

interface BuildingRepository {
    suspend fun getBuildingsInfoById(id: Int): BuildingSummaryModel?
    suspend fun scrapBuilding(buildingLikeRequestDTO: BuildingLikeRequestDTO): Unit?
    suspend fun cancelScrapBuilding(id: Int): Unit?
}