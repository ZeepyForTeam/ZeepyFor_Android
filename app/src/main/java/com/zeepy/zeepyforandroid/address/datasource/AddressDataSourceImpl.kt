package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.ResponseAddressListDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Single
import javax.inject.Inject

class AddressDataSourceImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): AddressDataSource {
    override fun fetchAddressList(): Single<ResponseAddressListDTO> = zeepyApiService.getAddressList()
}