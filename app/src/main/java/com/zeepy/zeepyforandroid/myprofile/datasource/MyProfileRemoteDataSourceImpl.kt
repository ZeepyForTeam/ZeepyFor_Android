package com.zeepy.zeepyforandroid.myprofile.datasource

import android.util.Log
import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import retrofit2.Response
import javax.inject.Inject

class MyProfileRemoteDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): MyProfileRemoteDataSource  {

    override suspend fun submitWithdrawal(): Unit? {
        return zeepyApiService.deleteAccount().verify()
    }

    override suspend fun logout(): Unit? {
        return zeepyApiService.logout().verify()
    }
}

fun Response<Unit?>.verify(): Unit? {
    if (this.isSuccessful && this.code() in 200..299) {
        return this.body()
    } else {
        throw Exception("${this.code()}")
    }
}