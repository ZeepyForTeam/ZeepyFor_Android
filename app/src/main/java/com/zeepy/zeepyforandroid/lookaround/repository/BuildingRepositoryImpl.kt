package com.zeepy.zeepyforandroid.lookaround.repository


import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.datasource.RemoteBuildingDataSource
import com.zeepy.zeepyforandroid.lookaround.mapper.BuildingMapper.toDomainModel
import javax.inject.Inject

class BuildingRepositoryImpl @Inject constructor(
    private val dataSource: RemoteBuildingDataSource
): BuildingRepository {
    override suspend fun getBuildingsInfoById(id: Int): BuildingSummaryModel? {
        return dataSource.getBuildingInfoById(id)?.toDomainModel()
    }
}