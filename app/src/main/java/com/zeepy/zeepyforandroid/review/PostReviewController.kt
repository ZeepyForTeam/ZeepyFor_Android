package com.zeepy.zeepyforandroid.review

import com.zeepy.zeepyforandroid.review.data.dto.RequestWriteReview
import io.reactivex.Completable

interface PostReviewController {
    fun postReview(requestWriteReview: RequestWriteReview): Completable
}