package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.address.dto.SearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Single
import javax.inject.Inject

class NotAuthSearchAddressDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): NotAuthSearchAddressDataSource {
    override fun searchBuildingAddress(address: String): Single<ResponseSearchBuildingAddressDTO> = zeepyApiService.searchBuildingAddress(address)
}