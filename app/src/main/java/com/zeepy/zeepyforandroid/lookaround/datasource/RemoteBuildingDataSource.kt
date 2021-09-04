package com.zeepy.zeepyforandroid.lookaround.datasource

import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO

interface RemoteBuildingDataSource {
    suspend fun getBuildingInfoById(id: Int): ResponseBuildingInfoDTO?
}