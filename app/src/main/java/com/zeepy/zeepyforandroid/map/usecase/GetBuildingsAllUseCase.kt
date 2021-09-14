package com.zeepy.zeepyforandroid.map.usecase

import com.zeepy.zeepyforandroid.di.IoDispatcher
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.repository.BuildingsRepository
import com.zeepy.zeepyforandroid.map.usecase.util.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetBuildingsAllUseCase @Inject constructor(
    private val repository: BuildingsRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<GetBuildingsAllUseCase.Params, List<BuildingModel>>(coroutineDispatcher) {

    override suspend fun execute(parameter: Params): List<BuildingModel> {
        return repository.getBuildingsAll(parameter.page)
    }

    data class Params(
        val page: Int
    )

}