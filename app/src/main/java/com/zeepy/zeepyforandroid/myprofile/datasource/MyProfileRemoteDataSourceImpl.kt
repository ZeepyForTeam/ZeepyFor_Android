package com.zeepy.zeepyforandroid.myprofile.datasource

import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import retrofit2.Response
import javax.inject.Inject

class MyProfileRemoteDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): MyProfileRemoteDataSource  {

    override suspend fun submitWithdrawal(userEmail: String): Unit? {
        return zeepyApiService.deleteAccount(userEmail).verify()
    }

    override suspend fun logout(userEmail: String): Unit? {
        return zeepyApiService.logout(userEmail).verify()
    }
}

fun Response<Unit?>.verify(): Unit? {
    if (this.isSuccessful && this.code() in 200..299) {
        return this.body()
    } else {
        throw Exception("${this.code()}")
    }
}