package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.ResponseAddressListDTO
import com.zeepy.zeepyforandroid.address.dto.ResponseBuildingInfoDTO
import io.reactivex.Single

interface AddressDataSource {
    fun fetchAddressList(): Single<ResponseAddressListDTO>
    fun fetchBuildgingInfoByAddress(address: String): Single<ResponseBuildingInfoDTO>
}