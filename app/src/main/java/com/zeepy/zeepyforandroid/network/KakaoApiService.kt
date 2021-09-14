package com.zeepy.zeepyforandroid.network

import com.zeepy.zeepyforandroid.map.data.ResultSearchAddress
import com.zeepy.zeepyforandroid.map.data.ResultSearchKeyword
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApiService {
    companion object {
        const val BASE_URL = "https://dapi.kakao.com"
    }

    @GET("/v2/local/search/keyword.json")
    fun getSearchKeyword(
        @Header("Authorization") key: String,
        @Query("query") query: String,
        @Query("x") x: String,
        @Query("y") y: String
        // 매개변수 추가 가능
        // @Query("category_group_code") category: String

    ): Call<ResultSearchKeyword>

    @GET("/v2/local/search/address.json")
    fun getSearchAddress(
        @Header("Authorization") key: String,
        @Query("query") query: String
    ): Call<ResultSearchAddress>
}