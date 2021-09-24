package com.zeepy.zeepyforandroid.address.controller

import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class AddressControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): AddressController {
    override fun addAddress(addresses: AddressListDTO): Completable =
        zeepyApiService.addAddress(addresses)
}