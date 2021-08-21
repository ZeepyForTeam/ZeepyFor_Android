package com.zeepy.zeepyforandroid.address.controller

import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import io.reactivex.Completable

interface AddressController {
    fun addAddress(addresses: AddressListDTO): Completable
}