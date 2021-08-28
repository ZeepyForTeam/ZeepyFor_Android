package com.zeepy.zeepyforandroid.signin.controller

import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.signin.dto.request.RequestLoginDTO
import com.zeepy.zeepyforandroid.signin.dto.request.RequestSocialSigninDTO
import io.reactivex.Single

interface SignInController {
    fun signin(requestLoginDTO: RequestLoginDTO): Single<ResponseAuthDTO>
    fun kakaoSignin(accessToken: RequestSocialSigninDTO): Single<ResponseAuthDTO>
    fun naverSignin(accessToken: RequestSocialSigninDTO): Single<ResponseAuthDTO>
}