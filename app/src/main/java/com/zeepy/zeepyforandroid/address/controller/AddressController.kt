package com.zeepy.zeepyforandroid.address.controller

import com.zeepy.zeepyforandroid.address.AddressEntity
import com.zeepy.zeepyforandroid.address.dto.ResponseAddressListDTO
import io.reactivex.Completable

interface AddressController {
    fun deleteAddress(addresses: ResponseAddressListDTO): Completable
}