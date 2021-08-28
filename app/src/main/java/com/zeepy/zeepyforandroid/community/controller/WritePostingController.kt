package com.zeepy.zeepyforandroid.community.controller

import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestParticipationDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWriteCommentDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import io.reactivex.Completable
import retrofit2.http.Body

interface WritePostingController {
    fun uploadPosting(requestWritePosting: RequestWritePosting): Completable
    fun writeComment(postingId: Int, requestWriteComment: RequestWriteCommentDTO): Completable
    fun participateGroupPurchase(postingId: Int, participationDTO: RequestParticipationDTO): Completable
    fun scrapPosting(postingId: Int): Completable
    fun cancelScrapPosting(postingId: Int): Completable
}