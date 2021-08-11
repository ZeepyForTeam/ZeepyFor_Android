package com.zeepy.zeepyforandroid.signin

import io.reactivex.Single

interface SignInController {
    fun signin(requestLogin: RequestLogin): Single<ResponseLogin>
}