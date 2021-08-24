package com.zeepy.zeepyforandroid.signup.controller

import com.zeepy.zeepyforandroid.signup.RequestSignUpDTO
import io.reactivex.Completable
import retrofit2.http.Body

interface SignUpController {
    fun checkEmailRepetition(email: String): Completable
    fun checkNickNamRepetition(nickname: String): Completable
    fun signUp(@Body registrationReqDto: RequestSignUpDTO): Completable
}