package com.example.zeepyforandroid.network

import com.example.zeepyforandroid.community.data.remote.response.ResponsePostingList
import com.example.zeepyforandroid.review.data.dto.RequestWriteReview
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Query

interface ZeepyApiService {
    fun writeReview(requestWriteReview: RequestWriteReview): Completable
    fun getPostingList(@Query("address") address: String, @Query("communityType") communityType: String?): Single<List<ResponsePostingList>>
}