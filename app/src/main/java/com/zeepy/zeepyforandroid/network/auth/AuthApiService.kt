package com.zeepy.zeepyforandroid.network.auth

import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.signin.RequestLogin
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/user/redundancy/email")
    fun checkEmailRepetition(@Body email: String): Completable

    @POST("/api/user/redundancy/nickname")
    fun checkNickNamRepetition(@Body nickname: String): Completable

    @POST("/api/auth/login")
    fun signin(@Body requestLogin: RequestLogin): Single<ResponseAuthDTO>

    @POST("/api/auth/reissue")
    fun fetchAccessToken(@Body reIssueReqDto: RequestTokenDTO): Response<ResponseAuthDTO>
}