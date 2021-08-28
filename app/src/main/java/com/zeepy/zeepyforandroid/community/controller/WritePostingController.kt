package com.zeepy.zeepyforandroid.community.controller

import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWriteCommentDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import io.reactivex.Completable
import retrofit2.http.Body

interface WritePostingController {
    fun uploadPosting(requestWritePosting: RequestWritePosting): Completable
    fun writeComment(communityId: Int, requestWriteComment: RequestWriteCommentDTO): Completable
}