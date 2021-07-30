package com.example.zeepyforandroid.network

import com.example.zeepyforandroid.review.data.RequestWriteReview

interface ZeepyApiService {
    fun writeReview(requestWriteReview: RequestWriteReview)
}