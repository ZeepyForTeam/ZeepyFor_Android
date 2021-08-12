package com.zeepy.zeepyforandroid.signin

import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.AuthApiService
import io.reactivex.Single
import javax.inject.Inject

class SignInControllerImpl @Inject constructor(
    private val authApiService: AuthApiService
): SignInController {
    override fun signin(requestLogin: RequestLogin): Single<ResponseAuthDTO> = authApiService.signin(requestLogin)
}