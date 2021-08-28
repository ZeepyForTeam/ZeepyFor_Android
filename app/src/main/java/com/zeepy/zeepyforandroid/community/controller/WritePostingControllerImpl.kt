package com.zeepy.zeepyforandroid.community.controller

import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWriteCommentDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Completable
import javax.inject.Inject

class WritePostingControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
) : WritePostingController {
    override fun uploadPosting(requestWritePosting: RequestWritePosting): Completable =
        zeepyApiService.uploadPosting(requestWritePosting)

    override fun writeComment(
        communityId: Int,
        requestWriteComment: RequestWriteCommentDTO
    ): Completable =
        zeepyApiService.postComment(communityId, requestWriteComment)
}
