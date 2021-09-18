package com.zeepy.zeepyforandroid.map.repository

import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.datasource.RemoteBuildingsDataSource
import com.zeepy.zeepyforandroid.map.datasource.RemoteBuildingsDataSourceImpl
import com.zeepy.zeepyforandroid.map.mapper.BuildingMapper.toBuildingSummaryDomainModel
import com.zeepy.zeepyforandroid.map.mapper.BuildingMapper.toDomainModel
import javax.inject.Inject

class BuildingsRepositoryImpl @Inject constructor(
    private val dataSource: RemoteBuildingsDataSource
): BuildingsRepository {
    override suspend fun getBuildingsInfoByLocation(
        latitudeGreater: Double,
        latitudeLess: Double,
        longitudeGreater: Double,
        longitudeLess: Double
    ): List<BuildingModel> {
        return dataSource.getBuildingsInfoByLocation(latitudeGreater, latitudeLess, longitudeGreater, longitudeLess).toDomainModel()
    }

    override suspend fun getBuildingsAll(page: Int): List<BuildingModel> {
        return dataSource.getBuildingsAll(page).toDomainModel()
    }

    override suspend fun getBuildingsUserLike(page: Int): List<BuildingSummaryModel> {
        return dataSource.getBuildingsUserLike(page).toBuildingSummaryDomainModel()
    }
}