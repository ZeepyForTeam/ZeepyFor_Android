package com.zeepy.zeepyforandroid.map.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeepy.zeepyforandroid.BuildConfig
import com.zeepy.zeepyforandroid.di.NetworkModule
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.data.ResultSearchAddress
import com.zeepy.zeepyforandroid.map.data.ResultSearchKeyword
import com.zeepy.zeepyforandroid.map.mapper.BuildingMapper.toDomainModel
import com.zeepy.zeepyforandroid.map.usecase.GetBuildingsByLocationUseCase
import com.zeepy.zeepyforandroid.map.usecase.util.Result
import com.zeepy.zeepyforandroid.map.usecase.util.data
import com.zeepy.zeepyforandroid.map.usecase.util.succeeded
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.daum.mf.map.api.MapPoint
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getBuildingsByLocationUseCase: GetBuildingsByLocationUseCase
) : ViewModel() {
    private val _markers = MutableLiveData<List<BuildingModel>>()
    val markers: LiveData<List<BuildingModel>> = _markers

    private val _buildingSelected = MutableLiveData<BuildingModel>()
    val buildingSelected: LiveData<BuildingModel> = _buildingSelected

    private val _buildingSelectedId = MutableLiveData<Int>()
    val buildingSelectedId: LiveData<Int> = _buildingSelectedId

    private val _fetchBuildingsResponse = MutableLiveData<Result<List<BuildingModel>>>()
    val fetchBuildingsResponse: LiveData<Result<List<BuildingModel>>> = _fetchBuildingsResponse

    private val _resultSearchedBuildings = MutableLiveData<List<BuildingModel>>()
    val resultSearchedBuildings: LiveData<List<BuildingModel>>
        get() = _resultSearchedBuildings

    private val kakaoApi = NetworkModule.kakaoApiService

    private val _currentCenterPoint = MutableLiveData<MapPoint>()
    val currentCenterPoint: LiveData<MapPoint>
        get() = _currentCenterPoint

    private val _placeSelectedFromSearch = MutableLiveData<BuildingModel>()
    val placeSelectedFromSearch: LiveData<BuildingModel>
        get() = _placeSelectedFromSearch

    init {
        _buildingSelectedId.value = -1
    }

    fun updatePlaceSelectedFromSearch(building: BuildingModel) {
        _placeSelectedFromSearch.value = building
    }

    fun updateCenterPoint(mapPoint: MapPoint) {
        _currentCenterPoint.value = mapPoint
    }

    fun updateBuildingSelected(building: BuildingModel) {
        _buildingSelected.value = building
    }

    fun updateBuildingSelectedId(id: Int) {
        _buildingSelectedId.value = id
    }

    fun getBuildingsByLocation(
        latitudeGreater: Double,
        latitudeLess: Double,
        longitudeGreater: Double,
        longitudeLess: Double
    ) {
        viewModelScope.launch {
            val result = getBuildingsByLocationUseCase(
                GetBuildingsByLocationUseCase.Params(
                    latitudeGreater,
                    latitudeLess,
                    longitudeGreater,
                    longitudeLess
                )
            )
            if (result.succeeded) {
                _fetchBuildingsResponse.value = result
                Log.e("response for getBuildingsByLocation", "" + result.data)
            }
        }
    }

    fun searchBuildingByKeyword(address: String) {
        kakaoApi.getSearchKeyword(
            BuildConfig.KAKAO_REST_API_KEY,
            query = address,
            x = currentCenterPoint.value?.mapPointGeoCoord?.longitude?.toBigDecimal()?.toPlainString()!!,
            y = currentCenterPoint.value?.mapPointGeoCoord?.latitude?.toBigDecimal()?.toPlainString()!!
        )
            .enqueue(object : retrofit2.Callback<ResultSearchKeyword> {
                override fun onResponse(
                    call: Call<ResultSearchKeyword>,
                    response: Response<ResultSearchKeyword>
                ) {
                    _resultSearchedBuildings.value = response.body()?.toDomainModel()

                    Log.e("kakao map search results (Keyword)", response.body().toString())
                    Log.e("response raw", response.raw().toString())
                }

                override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    fun searchBuildingByAddress(address: String) {
        kakaoApi.getSearchAddress(BuildConfig.KAKAO_REST_API_KEY, query = address)
            .enqueue(object : retrofit2.Callback<ResultSearchAddress> {
                override fun onResponse(
                    call: Call<ResultSearchAddress>,
                    response: Response<ResultSearchAddress>
                ) {
                    _resultSearchedBuildings.value = response.body()?.toDomainModel()

                    Log.e("kakao map search results (Address)", response.body().toString())
                    Log.e("response raw", response.raw().toString())
                }

                override fun onFailure(call: Call<ResultSearchAddress>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }


}

