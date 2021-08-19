package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.building.ResponseBuildingDTO
import io.reactivex.Single

interface NotAuthSearchAddressDataSource {
    fun searchBuildingAddress(address: String): Single<ResponseSearchBuildingAddressDTO>
}