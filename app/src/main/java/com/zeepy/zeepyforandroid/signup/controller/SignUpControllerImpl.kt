package com.zeepy.zeepyforandroid.signup.controller

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.signup.RequestSignUpDTO
import com.zeepy.zeepyforandroid.signup.controller.SignUpController
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class SignUpControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
): SignUpController {
    override fun checkEmailRepetition(email: String): Completable = zeepyApiService.checkEmailRepetition(email)
    override fun checkNickNamRepetition(nickname: String): Completable = zeepyApiService.checkNickNamRepetition(nickname)
    override fun signUp(registrationReqDto: RequestSignUpDTO): Completable = zeepyApiService.signUp(registrationReqDto)
}