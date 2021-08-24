package com.zeepy.zeepyforandroid.signup

data class RequestSignUpDTO(
    val email: String,
    val name: String,
    val nickname: String,
    val password: String,
    val sendMainCheck: Boolean
)