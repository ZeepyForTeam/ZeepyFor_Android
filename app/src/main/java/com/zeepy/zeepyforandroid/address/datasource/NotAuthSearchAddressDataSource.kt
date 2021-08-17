package com.zeepy.zeepyforandroid.address.datasource

import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.address.dto.SearchBuildingAddressDTO
import io.reactivex.Single

interface NotAuthSearchAddressDataSource {
    fun searchBuildingAddress(address: String): Single<ResponseSearchBuildingAddressDTO>
}