package com.example.zeepyforandroid.network

import com.example.zeepyforandroid.review.data.dto.RequestWriteReview
import io.reactivex.Completable

interface ZeepyApiService {
    fun writeReview(requestWriteReview: RequestWriteReview): Completable
}