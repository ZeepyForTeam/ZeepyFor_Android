package com.zeepy.zeepyforandroid.network.auth.dto

data class ResponseAuthDTO(
    val accessToken: String,
    val refreshToken: String,
    val userEmail: String,
    val userId: Int
)