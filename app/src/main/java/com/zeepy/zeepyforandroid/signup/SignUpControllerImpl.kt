package com.zeepy.zeepyforandroid.signup

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.AuthApiService
import io.reactivex.Completable
import javax.inject.Inject

class SignUpControllerImpl @Inject constructor(
    private val authApiService: AuthApiService
): SignUpController {
    override fun checkEmailRepetition(email: String): Completable = authApiService.checkEmailRepetition(email)

    override fun checkNickNamRepetition(nickname: String): Completable = authApiService.checkNickNamRepetition(nickname)
}