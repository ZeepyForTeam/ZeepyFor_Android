package com.example.zeepyforandroid.map.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.zeepyforandroid.base.BaseViewModel
import com.example.zeepyforandroid.map.data.ResultListener
import com.example.zeepyforandroid.map.data.geocoding.GeocodingDataSource
import com.example.zeepyforandroid.network.response.GeocodingResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor (
    private val geocodingDataSource: GeocodingDataSource,
) : BaseViewModel(){

    private val _searchAddressData: MutableLiveData<GeocodingResponse> = MutableLiveData()
    val searchAddressData: LiveData<GeocodingResponse> = _searchAddressData

    fun getSearchAddress(query: String, listener: ResultListener<String>) {
        addDisposable(
            geocodingDataSource.searchAddress(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        if(meta?.totalCount!! > 0) {
                            _searchAddressData.value = this
                            listener.success("")
                        } else {
                            listener.fail()
                        }
                    }
                }, {
                    listener.fail(it.message)
                })
        )
    }

    fun getSearchAddressName(coords: String, listener: ResultListener<String?>) {
        addDisposable(
            geocodingDataSource.searchAddressName(coords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.run {
                        if(results?.size!! > 0) {
                            listener.success(results[0]?.convertAddress())
                        } else {
                            listener.fail()
                        }
                    }
                }, {
                    listener.fail(it.message)
                })
        )
    }

}

