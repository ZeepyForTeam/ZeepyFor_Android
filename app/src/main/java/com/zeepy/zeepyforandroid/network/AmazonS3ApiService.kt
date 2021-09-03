package com.zeepy.zeepyforandroid.network

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface AmazonS3ApiService {
    @Multipart
    @PUT
    fun uploadImageToPresignedUrl(@Url uploadUrl: String, @Part image: MultipartBody.Part):Single<ResponseBody>
}