package com.zeepy.zeepyforandroid.map.usecase

import com.zeepy.zeepyforandroid.di.IoDispatcher
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.repository.BuildingsRepository
import com.zeepy.zeepyforandroid.map.usecase.util.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetBuildingsUserLikeUseCase @Inject constructor(
    private val repository: BuildingsRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<GetBuildingsUserLikeUseCase.Params, List<BuildingSummaryModel>>(coroutineDispatcher) {

    override suspend fun execute(parameter: Params): List<BuildingSummaryModel> {
        return repository.getBuildingsUserLike(parameter.page)
    }

    data class Params(
        val page: Int
    )

}