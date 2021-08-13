package com.zeepy.zeepyforandroid.network.auth.dto

data class RequestTokenDTO(
    val accessToken: String,
    val refreshToken: String
)