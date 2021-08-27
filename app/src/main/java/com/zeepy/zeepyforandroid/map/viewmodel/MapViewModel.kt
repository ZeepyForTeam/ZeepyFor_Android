package com.zeepy.zeepyforandroid.map.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.usecase.GetBuildingsByLocationUseCase
import com.zeepy.zeepyforandroid.map.usecase.util.data
import com.zeepy.zeepyforandroid.map.usecase.util.succeeded
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getBuildingsByLocationUseCase: GetBuildingsByLocationUseCase
    ): ViewModel() {
    private val _markers = MutableLiveData<List<BuildingModel>>()
    val markers: LiveData<List<BuildingModel>> = _markers

    private val _buildingId = MutableLiveData(-1)
    val buildingId: LiveData<Int> = _buildingId

    fun setMarkerClick(buildingId: Int) {
        _buildingId.value = buildingId
    }

    // test init
    init {
        try {
            getBuildingsByLocation(37.507308, 37.507114, 126.963345, 126.955746)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getBuildingsByLocation(latitudeGreater: Double, latitudeLess: Double, longitudeGreater: Double, longitudeLess: Double) {
        viewModelScope.launch {
            val result = getBuildingsByLocationUseCase(GetBuildingsByLocationUseCase.Params(latitudeGreater, latitudeLess, longitudeGreater, longitudeLess))
            if (result.succeeded) {
                _markers.value = result.data
                Log.e("response for getBuildingsByLocation", "" + result)
            } else {
                Log.e("error", "response fail")
            }
        }
    }



}

