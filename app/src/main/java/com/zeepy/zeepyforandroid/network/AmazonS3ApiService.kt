package com.zeepy.zeepyforandroid.network

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AmazonS3ApiService {
    @Multipart
    @HTTP(method = "PUT" ,hasBody = true)
    fun uploadImageToPresignedUrl(@Url uploadUrl: String, @Part image: MultipartBody.Part):Completable
}