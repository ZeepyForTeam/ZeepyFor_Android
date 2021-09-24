package com.zeepy.zeepyforandroid.imagecontrol

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.rxjava3.core.Observable

import okhttp3.MultipartBody
import javax.inject.Inject

class ImageControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): ImageController {
    override fun getPresignedUrl(): Observable<PreSignedUrlDTO> =
        zeepyApiService.getPresignedUrl()
}