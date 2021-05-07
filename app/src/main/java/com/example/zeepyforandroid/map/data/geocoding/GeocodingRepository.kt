package com.example.zeepyforandroid.map.data.geocoding

import com.example.zeepyforandroid.network.api.NaverApiService
import com.example.zeepyforandroid.network.response.GeocodingResponse
import com.example.zeepyforandroid.network.response.ReverseGeocodingResponse
import io.reactivex.Single

class GeocodingRepository(private val api: NaverApiService) : GeocodingDataSource {
    override fun searchAddress(query: String): Single<GeocodingResponse> = api.searchAddress(query)

    override fun searchAddressName(coords: String): Single<ReverseGeocodingResponse> = api.searchAddressName(coords, "roadaddr","json")
}