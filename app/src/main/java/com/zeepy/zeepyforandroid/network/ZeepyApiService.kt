package com.zeepy.zeepyforandroid.network

import com.zeepy.zeepyforandroid.address.AddressEntity
import com.zeepy.zeepyforandroid.address.ResponseAddressListDTO
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingList
import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.review.data.dto.RequestWriteReview
import com.zeepy.zeepyforandroid.signin.RequestLogin
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ZeepyApiService {

    @POST("/api/user/redundancy/email")
    fun checkEmailRepetition(@Body email: String): Completable

    @POST("/api/user/redundancy/nickname")
    fun checkNickNamRepetition(@Body nickname: String): Completable

    @POST("/api/auth/login")
    fun signin(@Body requestLogin: RequestLogin): Single<ResponseAuthDTO>

    @POST("/api/auth/reissue")
    fun fetchAccessToken(@Body reIssueReqDto: RequestTokenDTO): Response<ResponseAuthDTO>

    @POST("/api/review")
    fun writeReview(@Body reviewDto: RequestWriteReview): Completable

    @GET("/api/community")
    fun getPostingList(@Query("address") address: String, @Query("communityType") communityType: String?): Single<List<ResponsePostingList>>

    @GET("/api/user/address")
    fun getAddressList(): Single<ResponseAddressListDTO>
}