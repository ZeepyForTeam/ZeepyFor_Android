package com.zeepy.zeepyforandroid.address.controller

import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import io.reactivex.rxjava3.core.Completable

interface AddressController {
    fun addAddress(addresses: AddressListDTO): Completable
}