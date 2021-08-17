package com.zeepy.zeepyforandroid.lookaround.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BuildingDetailViewModel @Inject constructor(): ViewModel() {
    private val _buildingSummary = MutableLiveData<BuildingSummaryModel>()
    val buildingSummary: LiveData<BuildingSummaryModel>
        get() = _buildingSummary

    fun changeSummary(buildingSummarySelected: BuildingSummaryModel) {
        _buildingSummary.value = buildingSummarySelected
    }
}