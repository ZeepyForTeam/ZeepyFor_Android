package com.zeepy.zeepyforandroid.signup.controller

import io.reactivex.Completable

interface SignUpController {
    fun checkEmailRepetition(email: String): Completable
    fun checkNickNamRepetition(nickname: String): Completable
}