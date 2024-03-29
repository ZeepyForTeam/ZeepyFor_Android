package com.zeepy.zeepyforandroid.network.auth.controller

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.controller.TokenController
import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class TokenControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService,
    private val userPreferenceManager: UserPreferenceManager
) : TokenController {
    override fun fetchAccessToken(): Call<ResponseAuthDTO> =
        zeepyApiService.fetchAccessToken(
            RequestTokenDTO(
                userPreferenceManager.fetchUserAccessToken(),
                userPreferenceManager.fetchUserRefreshToken()
            )
        )
}