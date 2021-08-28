package com.zeepy.zeepyforandroid.imagecontrol

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody

interface ImageController {
    fun getPresignedUrl(): Observable<PreSignedUrlDTO>
}