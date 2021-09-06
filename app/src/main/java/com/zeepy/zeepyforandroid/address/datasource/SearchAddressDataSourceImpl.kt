package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class SearchAddressDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): SearchAddressDataSource {
    override fun searchBuildingAddress(address: String): Single<ResponseSearchBuildingAddressDTO> = zeepyApiService.searchBuildingAddress(address)
    override suspend fun searchBuildingsByAddress(address: String, page: Int): ResponseSearchBuildingAddressDTO? = zeepyApiService.searchBuildingsByAddress(address, page).verify()

}

fun Response<ResponseSearchBuildingAddressDTO>.verify(): ResponseSearchBuildingAddressDTO? {
    if (this.isSuccessful && this.code() in 200..299) {
        return this.body()
    } else {
        throw Exception("${this.code()}")
    }
}