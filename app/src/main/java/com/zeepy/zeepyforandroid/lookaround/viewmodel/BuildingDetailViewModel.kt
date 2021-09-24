package com.zeepy.zeepyforandroid.lookaround.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeepy.zeepyforandroid.building.BuildingLikeRequestDTO
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.repository.BuildingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BuildingDetailViewModel @Inject constructor(
    private val repository: BuildingRepository
): ViewModel() {
    private val _buildingSummary = MutableLiveData<BuildingSummaryModel>()
    val buildingSummary: LiveData<BuildingSummaryModel>
        get() = _buildingSummary

    private val _buildingId = MutableLiveData<Int>()
    val buildingId: LiveData<Int>
        get() = _buildingId

    fun changeSummary(buildingSummarySelected: BuildingSummaryModel) {
        _buildingSummary.value = buildingSummarySelected
    }

    fun changeBuildingId(id: Int) {
        _buildingId.value = id
    }

    fun scrapBuilding() {
        viewModelScope.launch {
            try {
                val result = repository.scrapBuilding(BuildingLikeRequestDTO(_buildingId.value!!))
            } catch(e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun cancelScrapBuilding() {
        viewModelScope.launch {
            try {
                val result = repository.cancelScrapBuilding(_buildingId.value!!)
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}