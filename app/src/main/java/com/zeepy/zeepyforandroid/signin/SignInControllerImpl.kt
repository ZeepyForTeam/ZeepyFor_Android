package com.zeepy.zeepyforandroid.signin

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import io.reactivex.Single
import javax.inject.Inject

class SignInControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): SignInController {
    override fun signin(requestLogin: RequestLogin): Single<ResponseAuthDTO> = zeepyApiService.signin(requestLogin)
}