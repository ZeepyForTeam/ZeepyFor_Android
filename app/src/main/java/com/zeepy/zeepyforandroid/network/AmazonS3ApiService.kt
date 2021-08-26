package com.zeepy.zeepyforandroid.network

import io.reactivex.Completable
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface AmazonS3ApiService {
    @PUT
    @Multipart
    fun uploadImageToPresignedUrl(@Part image: MultipartBody.Part): Completable
}