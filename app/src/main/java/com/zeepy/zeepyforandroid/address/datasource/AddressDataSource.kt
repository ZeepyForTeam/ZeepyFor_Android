package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import io.reactivex.rxjava3.core.Single

interface AddressDataSource {
    fun fetchAddressList(): Single<AddressListDTO>
    fun fetchBuildgingInfoByAddress(address: String): Single<ResponseBuildingInfoDTO>
}