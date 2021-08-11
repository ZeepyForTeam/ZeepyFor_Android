package com.zeepy.zeepyforandroid.signin

data class ResponseLogin(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)