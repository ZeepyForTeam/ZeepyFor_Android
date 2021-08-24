package com.zeepy.zeepyforandroid.map.usecase

import com.zeepy.zeepyforandroid.di.IoDispatcher
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.repository.BuildingsRepository
import com.zeepy.zeepyforandroid.map.usecase.util.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetBuildingsByLocationUseCase @Inject constructor(
    private val repository: BuildingsRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<GetBuildingsByLocationUseCase.Params, List<BuildingModel>>(coroutineDispatcher){

    override suspend fun execute(parameter: Params): List<BuildingModel> {
        return repository.getBuildingsInfoByLocation(parameter.latitudeGreater, parameter.latitudeLess, parameter.longitudeGreater, parameter.longitudeLess)
    }

    data class Params(
        val latitudeGreater: Double,
        val latitudeLess: Double,
        val longitudeGreater: Double,
        val longitudeLess: Double
    )
}