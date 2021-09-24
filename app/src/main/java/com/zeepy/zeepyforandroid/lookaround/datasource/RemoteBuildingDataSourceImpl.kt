package com.zeepy.zeepyforandroid.lookaround.datasource

import com.zeepy.zeepyforandroid.building.BuildingLikeRequestDTO
import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteBuildingDataSourceImpl@Inject constructor(
    private val zeepyApiService: ZeepyApiService
): RemoteBuildingDataSource  {
    override suspend fun getBuildingInfoById(id: Int): ResponseBuildingInfoDTO? {
        return zeepyApiService.getBuildingById(id).verifyBuildingInfo()
    }

    override suspend fun scrapBuilding(buildingLikeRequestDTO: BuildingLikeRequestDTO): Unit? {
        return zeepyApiService.scrapBuilding(buildingLikeRequestDTO).verifyScrapBuildingResponse()
    }

    override suspend fun cancelScrapBuilding(id: Int): Unit? {
        return zeepyApiService.cancelScrapBuilding(id).verifyScrapBuildingResponse()
    }
}

// TODO: Can change extension functions to accept generic type

fun Response<ResponseBuildingInfoDTO>.verifyBuildingInfo(): ResponseBuildingInfoDTO? {
    if (this.isSuccessful && this.code() in 200..299) {
        return this.body()
    } else {
        throw Exception("${this.code()}")
    }
}

fun Response<Unit?>.verifyScrapBuildingResponse(): Unit? {
    if (this.isSuccessful && this.code() in 200..299) {
        return this.body()
    } else {
        throw Exception("${this.code()}")
    }
}