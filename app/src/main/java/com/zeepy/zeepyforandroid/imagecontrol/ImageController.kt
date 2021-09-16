package com.zeepy.zeepyforandroid.imagecontrol

import io.reactivex.rxjava3.core.Observable

interface ImageController {
    fun getPresignedUrl(): Observable<PreSignedUrlDTO>
}