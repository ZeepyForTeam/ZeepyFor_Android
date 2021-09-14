package com.zeepy.zeepyforandroid.localdata

import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.building.BuildingDealDTO
import com.zeepy.zeepyforandroid.building.BuildingLikeDTO
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO
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
    suspend fun insertBuildingDeals(building: BuildingSummaryModel, buildingId: Int)
    suspend fun insertBuildingLikes(building: BuildingSummaryModel, buildingId: Int)
    suspend fun insertBuildingReviews(building: BuildingSummaryModel, buildingId: Int)
    suspend fun deleteBuilding(id: Int)
    suspend fun isRowExists(id: Int): Boolean
}