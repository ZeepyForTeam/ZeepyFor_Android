package com.zeepy.zeepyforandroid.imagecontrol

import io.reactivex.Observable
import io.reactivex.Single

interface ImageController {
    fun getPresignedUrl(): Observable<PreSignedUrlDTO>
}