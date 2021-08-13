package com.zeepy.zeepyforandroid.lookaround.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.lookaround.data.entity.LookAroundBuildingSummaryModel

class LookAroundViewModel {
    private val _buildingList = MutableLiveData<List<LookAroundBuildingSummaryModel>>()
    val buildingList: LiveData<List<LookAroundBuildingSummaryModel>>
        get() = _buildingList

}