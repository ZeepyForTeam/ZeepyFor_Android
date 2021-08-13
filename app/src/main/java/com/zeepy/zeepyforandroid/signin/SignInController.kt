package com.zeepy.zeepyforandroid.signin

import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import io.reactivex.Single

interface SignInController {
    fun signin(requestLogin: RequestLogin): Single<ResponseAuthDTO>
}