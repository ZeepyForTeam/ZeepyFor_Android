package com.zeepy.zeepyforandroid.localdata

import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.localdata.mapper.BuildingMapper.toDomain
import com.zeepy.zeepyforandroid.localdata.mapper.BuildingMapper.toEntity
import com.zeepy.zeepyforandroid.localdata.mapper.DealMapper.toEntity
import com.zeepy.zeepyforandroid.localdata.mapper.LikeMapper.toEntity
import com.zeepy.zeepyforandroid.localdata.mapper.ReviewMapper.toEntity
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import io.reactivex.rxjava3.core.Maybe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ZeepyLocalRepositoryImpl @Inject constructor(
    private val zeepyLocalDatabase: ZeepyDatabase
) : ZeepyLocalRepository {
    val zeepyDao = zeepyLocalDatabase.getzeepyDao()
    override fun fetchAddressList(): Maybe<List<LocalAddressEntity>> = zeepyDao.fetchAddressList()
    override fun insertAllAddress(addressList: List<LocalAddressEntity>) = zeepyDao.insertAllAddress(addressList)
    override fun insertAddress(address: LocalAddressEntity) = zeepyDao.insertAddress(address)
    override fun deleteAddress(address: LocalAddressEntity) = zeepyDao.deleteAddress(address)
    override fun deleteEveryAddress() = zeepyDao.deleteEveryAddress()

    val buildingDao = zeepyLocalDatabase.buildingDao()
    override fun fetchBuildingById(id: Int): Flow<BuildingSummaryModel> = buildingDao.getBuildingById(id).map { it.toDomain() }.distinctUntilChanged()
    override suspend fun insertBuilding(building: BuildingSummaryModel) {
        building.toEntity().let { buildingDao.insertBuilding(it) }
    }

    override suspend fun insertBuildingDeals(building: BuildingSummaryModel, buildingId: Int) {
        building.buildingDeals.toEntity(buildingId).forEach { buildingDao.insertBuildingDeals(it) }
    }

    override suspend fun insertBuildingLikes(building: BuildingSummaryModel, buildingId: Int) {
        building.buildingLikes.toEntity(buildingId).forEach { buildingDao.insertBuildingLikes(it) }
    }

    override suspend fun insertBuildingReviews(building: BuildingSummaryModel, buildingId: Int) {
        building.reviews.toEntity(buildingId).forEach { buildingDao.insertBuildingReviews(it) }
    }

    override suspend fun deleteBuilding(id: Int) = buildingDao.deleteBuildingById(id)
    override suspend fun isRowExists(id: Int): Boolean = buildingDao.isRowExists(id)
}