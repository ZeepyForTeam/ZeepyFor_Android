package com.zeepy.zeepyforandroid.localdata

import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import io.reactivex.Maybe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface ZeepyLocalRepository {
    fun fetchAddressList(): Maybe<List<LocalAddressEntity>>
    fun insertAllAddress(addressList: List<LocalAddressEntity>)
    fun insertAddress(address: LocalAddressEntity)
    fun deleteAddress(address: LocalAddressEntity)
    fun deleteEveryAddress()

    fun fetchBuildingById(id: Int): Flow<BuildingSummaryModel>
    suspend fun insertBuilding(building: BuildingSummaryModel)
    suspend fun deleteBuilding(id: Int)
    fun isRowExists(id: Int): Boolean
}