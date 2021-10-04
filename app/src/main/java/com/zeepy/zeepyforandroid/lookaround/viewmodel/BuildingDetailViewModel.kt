package com.zeepy.zeepyforandroid.lookaround.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeepy.zeepyforandroid.building.BuildingLikeRequestDTO
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.repository.BuildingRepository
import com.zeepy.zeepyforandroid.map.usecase.GetBuildingsUserLikeUseCase
import com.zeepy.zeepyforandroid.map.usecase.util.Result
import com.zeepy.zeepyforandroid.map.usecase.util.data
import com.zeepy.zeepyforandroid.map.usecase.util.succeeded
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class BuildingDetailViewModel @Inject constructor(
    private val repository: BuildingRepository,
    private val getBuildingsUserLikeUseCase: GetBuildingsUserLikeUseCase,
    private val zeepyLocalRepository: ZeepyLocalRepository,
    private val userPreferenceManager: UserPreferenceManager
) : ViewModel() {
    private val _buildingSummary = MutableLiveData<BuildingSummaryModel>()
    val buildingSummary: LiveData<BuildingSummaryModel>
        get() = _buildingSummary

    private val _buildingId = MutableLiveData<Int>()
    val buildingId: LiveData<Int>
        get() = _buildingId

    private val _buildingLikeId = MutableLiveData<Int>()
    val buildingLikeId: LiveData<Int>
        get() = _buildingLikeId

    private val _buildingsUserLikes = MutableLiveData<Result<List<BuildingSummaryModel>>>()
    val buildingsUserLikes: LiveData<Result<List<BuildingSummaryModel>>>
        get() = _buildingsUserLikes

    private val _isUserLike = MutableLiveData<Boolean>()
    val isUserLike: LiveData<Boolean>
        get() = _isUserLike

    fun changeSummary(buildingSummarySelected: BuildingSummaryModel) {
        _buildingSummary.value = buildingSummarySelected
    }

    fun changeBuildingId(id: Int) {
        _buildingId.value = id
    }

    fun checkIfUserLikesBuilding() {
        _buildingsUserLikes.value?.data?.forEach {
            if (it.id == _buildingId.value) {
                _isUserLike.value = true
            }
        }
    }

    fun setBuildingsUserLikes() {
        viewModelScope.launch {
            val result = getBuildingsUserLikeUseCase(
                GetBuildingsUserLikeUseCase.Params(0)
            ) // FIXME: paging needed

            if (result.succeeded) {
                _buildingsUserLikes.value = result
            }

        }
    }

    fun scrapBuilding() {
        viewModelScope.launch {
            try {
                val result = repository.scrapBuilding(BuildingLikeRequestDTO(_buildingId.value!!))
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun cancelScrapBuilding() = runBlocking {
        val job = launch {
            try {
                getLikeIdByBuildingId(_buildingId.value!!)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        job.join()
        _buildingLikeId.value?.let { repository.cancelScrapBuilding(it) }
    }

    private suspend fun getLikeIdByBuildingId(id: Int) {
        val result = repository.getBuildingsInfoById(id)
        if (result == null) {
            getBuildingInfoFromLocal(id)
        }
        result?.buildingLikes?.forEach {
            if (it.userId == userPreferenceManager.fetchUserId().toString()) {
                _buildingLikeId.value = it.id
            }
        }
    }

    private suspend fun getBuildingInfoFromLocal(id: Int) {
        try {
            zeepyLocalRepository.fetchBuildingById(id).collect {
                it.buildingLikes.forEach { buildingLike ->
                    if (buildingLike.userId == userPreferenceManager.fetchUserNickname()) {
                        _buildingLikeId.value = buildingLike.id
                    }
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}