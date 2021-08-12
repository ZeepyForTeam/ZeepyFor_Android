package com.zeepy.zeepyforandroid.network.auth

import com.kakao.sdk.network.ApiFactory
import com.zeepy.zeepyforandroid.BuildConfig
import com.zeepy.zeepyforandroid.di.NetworkModule
import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authApiService: AuthApiService,
    private val userPreferenceManager: UserPreferenceManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val updatedToken = getNewToken()
            updatedToken?.let {
                response.request.newBuilder().header("token", it)
                    .build()
            }
        } else {
            return null
//            response.request.newBuilder().header("token", userPreferenceManager.fetchUserAccessToken()).build()
        }
    }

    private fun getNewToken(): String? {
        val call = authApiService.fetchAccessToken(
            RequestTokenDTO(
                userPreferenceManager.fetchUserAccessToken(),
                userPreferenceManager.fetchUserRefreshToken()
            )
        )

        if (call.isSuccessful) {
            userPreferenceManager.saveUserRefreshToken(call.body()!!.refreshToken)
            userPreferenceManager.saveUserAccessToken(call.body()!!.accessToken)
        }

        return call?.body()?.accessToken
    }
}