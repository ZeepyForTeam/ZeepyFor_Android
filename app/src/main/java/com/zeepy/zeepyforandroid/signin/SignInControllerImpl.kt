package com.zeepy.zeepyforandroid.signin

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import io.reactivex.Single
import javax.inject.Inject

class SignInControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): SignInController {
    override fun signin(requestLogin: RequestLogin): Single<ResponseLogin> = zeepyApiService.signin(requestLogin)
}