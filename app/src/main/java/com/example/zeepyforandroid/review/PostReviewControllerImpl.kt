package com.example.zeepyforandroid.review

import com.example.zeepyforandroid.network.ZeepyApiService
import com.example.zeepyforandroid.review.data.dto.RequestWriteReview
import io.reactivex.Completable
import javax.inject.Inject

class PostReviewControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
) : PostReviewController {
    override fun postReview(requestWriteReview: RequestWriteReview): Completable =
        zeepyApiService.writeReview(requestWriteReview)
}