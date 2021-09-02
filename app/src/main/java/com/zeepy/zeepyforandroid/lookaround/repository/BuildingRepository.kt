package com.zeepy.zeepyforandroid.lookaround.repository

import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel

interface BuildingRepository {
    suspend fun getBuildingsInfoById(id: Int): BuildingSummaryModel?
}