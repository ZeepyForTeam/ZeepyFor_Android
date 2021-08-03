package com.example.zeepyforandroid.review

import com.example.zeepyforandroid.review.data.dto.RequestWriteReview
import io.reactivex.Completable

interface PostReviewController {
    fun postReview(requestWriteReview: RequestWriteReview): Completable
}