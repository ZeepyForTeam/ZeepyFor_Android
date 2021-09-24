package com.zeepy.zeepyforandroid.signin.dto.response

data class ResponseSocialSignInDTO(
    val accessToken: String,
    val refreshToken: String,
    val userEmail: String,
    val userId: Int
)