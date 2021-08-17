package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.ResponseAddressListDTO
import io.reactivex.Single

interface AddressDataSource {
    fun fetchAddressList(): Single<ResponseAddressListDTO>
}