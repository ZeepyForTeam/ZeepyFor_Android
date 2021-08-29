package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Single
import javax.inject.Inject

class SearchAddressDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): SearchAddressDataSource {
    override fun searchBuildingAddress(address: String): Single<ResponseSearchBuildingAddressDTO> = zeepyApiService.searchBuildingAddress(address)
}