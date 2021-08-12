package com.zeepy.zeepyforandroid.review

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.review.data.dto.RequestWriteReview
import io.reactivex.Completable
import javax.inject.Inject

class PostReviewControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
) : PostReviewController {
    override fun postReview(requestWriteReview: RequestWriteReview): Completable =
        zeepyApiService.writeReview(requestWriteReview)
}