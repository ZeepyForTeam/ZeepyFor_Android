package com.zeepy.zeepyforandroid.address.controller

import com.zeepy.zeepyforandroid.address.AddressEntity
import com.zeepy.zeepyforandroid.address.controller.AddressController
import com.zeepy.zeepyforandroid.address.dto.ResponseAddressListDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Completable
import javax.inject.Inject

class AddressControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): AddressController {
    override fun deleteAddress(addresses: ResponseAddressListDTO): Completable =
        zeepyApiService.deleteAddress(addresses)
}