package com.zeepy.zeepyforandroid.map.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.usecase.GetBuildingsByLocationUseCase
import com.zeepy.zeepyforandroid.map.usecase.util.Result
import com.zeepy.zeepyforandroid.map.usecase.util.data
import com.zeepy.zeepyforandroid.map.usecase.util.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getBuildingsByLocationUseCase: GetBuildingsByLocationUseCase
    ): ViewModel() {
    private val _markers = MutableLiveData<List<BuildingModel>>()
    val markers: LiveData<List<BuildingModel>> = _markers

    private val _buildingSelected = MutableLiveData<BuildingModel>()
    val buildingSelected: LiveData<BuildingModel> = _buildingSelected

    private val _buildingSelectedId = MutableLiveData<Int>()
    val buildingSelectedId: LiveData<Int> = _buildingSelectedId

    private val _fetchBuildingsResponse = MutableLiveData<Result<List<BuildingModel>>>()
    val fetchBuildingsResponse: LiveData<Result<List<BuildingModel>>> = _fetchBuildingsResponse

    init {
        _buildingSelectedId.value = -1
    }

    fun updateBuildingSelected(building: BuildingModel) {
        _buildingSelected.value = building
    }

    fun updateBuildingSelectedId(id: Int) {
        _buildingSelectedId.value = id
    }

    fun getBuildingsByLocation(latitudeGreater: Double, latitudeLess: Double, longitudeGreater: Double, longitudeLess: Double) {
        viewModelScope.launch {
            val result = getBuildingsByLocationUseCase(GetBuildingsByLocationUseCase.Params(latitudeGreater, latitudeLess, longitudeGreater, longitudeLess))
            if (result.succeeded) {
                _markers.value = result.data
                Log.e("response for getBuildingsByLocation", "" + result.data)
            }
            _fetchBuildingsResponse.value = result
        }
    }



}

