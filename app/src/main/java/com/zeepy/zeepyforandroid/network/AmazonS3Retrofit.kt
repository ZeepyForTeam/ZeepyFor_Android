package com.zeepy.zeepyforandroid.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object AmazonS3Retrofit {
    fun createAmazonS3Retrofit(baseURL: String): AmazonS3ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(AmazonS3ApiService::class.java)
    }
}