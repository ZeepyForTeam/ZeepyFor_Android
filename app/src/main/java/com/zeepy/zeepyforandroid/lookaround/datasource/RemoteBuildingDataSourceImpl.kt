package com.zeepy.zeepyforandroid.lookaround.datasource

import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteBuildingDataSourceImpl@Inject constructor(
    private val zeepyApiService: ZeepyApiService
): RemoteBuildingDataSource  {
    override suspend fun getBuildingInfoById(id: Int): ResponseBuildingInfoDTO? {
        return zeepyApiService.getBuildingById(id).verify()
    }
}

fun Response<ResponseBuildingInfoDTO>.verify(): ResponseBuildingInfoDTO? {
    if (this.isSuccessful && this.code() in 200..299) {
        return this.body()
    } else {
        throw Exception("${this.code()}")
    }
}