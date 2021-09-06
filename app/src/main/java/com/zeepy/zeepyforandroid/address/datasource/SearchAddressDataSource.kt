package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import io.reactivex.Single

interface SearchAddressDataSource {
    fun searchBuildingAddress(address: String): Single<ResponseSearchBuildingAddressDTO>
    //coroutine
    suspend fun searchBuildingsByAddress(address: String, page: Int): ResponseSearchBuildingAddressDTO?
}