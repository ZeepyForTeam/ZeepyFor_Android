package com.zeepy.zeepyforandroid.network.auth.controller

import retrofit2.Call

import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenController {
    fun fetchAccessToken(): Response<ResponseAuthDTO>
}