package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import com.zeepy.zeepyforandroid.address.dto.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Single
import javax.inject.Inject

class AddressDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): AddressDataSource {
    override fun fetchAddressList(): Single<AddressListDTO> = zeepyApiService.getAddressList()
    override fun fetchBuildgingInfoByAddress(address: String): Single<ResponseBuildingInfoDTO> = zeepyApiService.getBuildingByAddress(address)
}