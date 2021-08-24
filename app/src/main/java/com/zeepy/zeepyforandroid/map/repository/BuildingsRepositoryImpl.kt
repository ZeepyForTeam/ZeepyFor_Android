package com.zeepy.zeepyforandroid.map.repository

import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.datasource.RemoteBuildingsDataSource
import com.zeepy.zeepyforandroid.map.datasource.RemoteBuildingsDataSourceImpl
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
}