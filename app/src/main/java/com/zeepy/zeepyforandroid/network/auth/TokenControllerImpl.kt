package com.zeepy.zeepyforandroid.network.auth

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class TokenControllerImpl @Inject constructor(
    private val authApiService: AuthApiService,
    private val userPreferenceManager: UserPreferenceManager
) : TokenController {
    override fun fetchAccessToken(): Response<ResponseAuthDTO> =
        authApiService.fetchAccessToken(
            RequestTokenDTO(
                userPreferenceManager.fetchUserAccessToken(),
                userPreferenceManager.fetchUserRefreshToken()
            )
        )
}