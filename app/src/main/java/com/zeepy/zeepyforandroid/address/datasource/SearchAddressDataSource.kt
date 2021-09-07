package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import io.reactivex.rxjava3.core.Single

interface SearchAddressDataSource {
    fun searchBuildingAddress(address: String): Single<ResponseSearchBuildingAddressDTO>
}