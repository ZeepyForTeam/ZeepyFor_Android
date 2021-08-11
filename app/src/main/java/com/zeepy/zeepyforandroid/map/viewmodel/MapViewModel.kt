package com.zeepy.zeepyforandroid.map.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeepy.zeepyforandroid.map.data.Building
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MapViewModel: ViewModel() {
    private val _markers = MutableLiveData<List<Building>>()
    val markers: LiveData<List<Building>> = _markers

    private val _buildingId = MutableLiveData(-1)
    val buildingId: LiveData<Int> = _buildingId

    fun setMarkerClick(buildingId: Int) {
        _buildingId.value = buildingId
    }

    fun requestBuildingData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            //TODO: RetrofitBuilder && postValue
        } catch (e: HttpException) {
            Log.d("request: ", e.toString())
        } catch (e: IOException) {
            this.cancel()
        }
    }
}

