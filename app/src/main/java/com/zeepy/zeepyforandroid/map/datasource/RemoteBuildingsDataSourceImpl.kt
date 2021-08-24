package com.zeepy.zeepyforandroid.map.datasource

import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import retrofit2.Response
import javax.inject.Inject

class RemoteBuildingsDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): RemoteBuildingsDataSource {
    override suspend fun getBuildingsInfoByLocation(latitudeGreater: Double, latitudeLess: Double, longitudeGreater: Double, longitudeLess: Double): List<ResponseBuildingInfoDTO> {
        return zeepyApiService.getBuildingsByLocation(latitudeGreater, latitudeLess, longitudeGreater, longitudeLess).verify()
    }
}

fun Response<List<ResponseBuildingInfoDTO>>.verify(): List<ResponseBuildingInfoDTO> {
    if (this.isSuccessful && this.code() in 200..299) {
        return this.body() ?: listOf()
    } else {
        throw Exception("${this.code()}")
    }
}