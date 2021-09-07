package com.zeepy.zeepyforandroid.signin.controller

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.signin.dto.request.RequestLoginDTO
import com.zeepy.zeepyforandroid.signin.dto.request.RequestSocialSigninDTO
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class SignInControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
) : SignInController {
    override fun signin(requestLoginDTO: RequestLoginDTO): Single<ResponseAuthDTO> =
        zeepyApiService.signin(requestLoginDTO)

    override fun kakaoSignin(accessToken: RequestSocialSigninDTO): Single<ResponseAuthDTO> =
        zeepyApiService.kakaoSignin(accessToken)

    override fun naverSignin(accessToken: RequestSocialSigninDTO): Single<ResponseAuthDTO> =
        zeepyApiService.naverSignin(accessToken)
}