package com.zeepy.zeepyforandroid.imagecontrol

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import javax.inject.Inject

class ImageControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): ImageController {
    override fun getPresignedUrl(): Observable<PreSignedUrlDTO> =
        zeepyApiService.getPresignedUrl()
}