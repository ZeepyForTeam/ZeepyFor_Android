package com.zeepy.zeepyforandroid.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AmazonS3Retrofit {
    private val loggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    val okhttpClient = OkHttpClient.Builder()
        .connectTimeout(200, TimeUnit.SECONDS)
        .writeTimeout(200, TimeUnit.SECONDS)
        .readTimeout(200, TimeUnit.SECONDS)
        .build()

    val interceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
                .newBuilder()
                .build()
            return chain.proceed(request)
        }
    }
    val client = okhttpClient.newBuilder()
        .addNetworkInterceptor(interceptor).build()

    fun createAmazonS3ApiService(): AmazonS3ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(S3_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(AmazonS3ApiService::class.java)
    }

    val S3_URL = "https://zeepy.s3.ap-northeast-2.amazonaws.com/zeepyImage/"

}