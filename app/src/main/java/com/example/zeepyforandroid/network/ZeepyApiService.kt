package com.example.zeepyforandroid.network

import com.example.zeepyforandroid.community.data.remote.response.ResponsePostingList
import com.example.zeepyforandroid.review.data.dto.RequestWriteReview
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ZeepyApiService {
    @POST("/api/user/redundancy/email")
    fun checkEmailRepetition(@Body email: String): Completable

    @POST("/api/user/redundancy/nickname")
    fun checkNickNamRepetition(@Body nickname: String): Completable

    @POST("/api/review")
    fun writeReview(@Body reviewDto: RequestWriteReview): Completable

    @GET("/api/community")
    fun getPostingList(@Query("address") address: String, @Query("communityType") communityType: String?): Single<List<ResponsePostingList>>
}