package com.zeepy.zeepyforandroid.signup

import io.reactivex.Completable

interface SignUpController {
    fun checkEmailRepetition(email: String): Completable
    fun checkNickNamRepetition(nickname: String): Completable
}