package com.zeepy.zeepyforandroid.community.controller

import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestParticipationDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestReportDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWriteCommentDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponseImageUrls
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody

interface WritePostingController {
    fun uploadPosting(requestWritePosting: RequestWritePosting): Completable
    fun writeComment(postingId: Int, requestWriteComment: RequestWriteCommentDTO): Completable
    fun participateGroupPurchase(postingId: Int, participationDTO: RequestParticipationDTO): Completable
    fun scrapPosting(postingId: Int): Completable
    fun cancelScrapPosting(postingId: Int): Completable
    fun cancelParticipation(postingId: Int): Completable
    fun deletePosting(postingId: Int): Completable
    fun uploadImages(imageList: ArrayList<MultipartBody.Part?>): Single<ResponseImageUrls>
    fun reportComment(requestReportDTO: RequestReportDTO): Completable
}