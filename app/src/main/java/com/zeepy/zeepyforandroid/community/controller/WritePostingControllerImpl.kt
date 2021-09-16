package com.zeepy.zeepyforandroid.community.controller

import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestParticipationDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestReportDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWriteCommentDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponseImageUrls
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import javax.inject.Inject

class WritePostingControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
) : WritePostingController {
    override fun uploadPosting(requestWritePosting: RequestWritePosting): Completable =
        zeepyApiService.uploadPosting(requestWritePosting)

    override fun writeComment(
        postingId: Int,
        requestWriteComment: RequestWriteCommentDTO
    ): Completable =
        zeepyApiService.postComment(postingId, requestWriteComment)

    override fun participateGroupPurchase(
        postingId: Int,
        participationDTO: RequestParticipationDTO
    ): Completable =
        zeepyApiService.participateGroupPurchase(postingId, participationDTO)

    override fun scrapPosting(postingId: Int): Completable =
        zeepyApiService.scrapPosting(postingId)

    override fun cancelScrapPosting(postingId: Int): Completable =
        zeepyApiService.cancelScrapPosting(postingId)

    override fun cancelParticipation(postingId: Int): Completable =
        zeepyApiService.cancelParticipation(postingId)

    override fun deletePosting(postingId: Int): Completable =
        zeepyApiService.deletePosting(postingId)

    override fun uploadImages(imageList: ArrayList<MultipartBody.Part?>): Single<ResponseImageUrls> =
        zeepyApiService.postImages(imageList)

    override fun reportComment(requestReportDTO: RequestReportDTO): Completable =
        zeepyApiService.reportComment(requestReportDTO)
}
