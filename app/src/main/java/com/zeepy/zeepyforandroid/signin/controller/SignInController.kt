package com.zeepy.zeepyforandroid.signin.controller

import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.signin.dto.RequestLogin
import io.reactivex.Single

interface SignInController {
    fun signin(requestLogin: RequestLogin): Single<ResponseAuthDTO>
}