package com.zeepy.zeepyforandroid.network

import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingList
import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.review.data.dto.RequestWriteReview
import com.zeepy.zeepyforandroid.signin.RequestLogin
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ZeepyApiService {

    @POST("/api/review")
    fun writeReview(@Body reviewDto: RequestWriteReview): Completable

    @GET("/api/community")
    fun getPostingList(@Query("address") address: String, @Query("communityType") communityType: String?): Single<List<ResponsePostingList>>

}