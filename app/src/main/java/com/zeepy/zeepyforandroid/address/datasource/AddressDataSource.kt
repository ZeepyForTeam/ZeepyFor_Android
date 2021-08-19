package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import com.zeepy.zeepyforandroid.address.dto.ResponseBuildingInfoDTO
import io.reactivex.Single

interface AddressDataSource {
    fun fetchAddressList(): Single<AddressListDTO>
    fun fetchBuildgingInfoByAddress(address: String): Single<ResponseBuildingInfoDTO>
}