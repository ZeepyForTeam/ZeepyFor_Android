package com.zeepy.zeepyforandroid.address

import io.reactivex.Single

interface AddressDataSource {
    fun fetchAddressList(): Single<List<ResponseAddressDTO>>
}