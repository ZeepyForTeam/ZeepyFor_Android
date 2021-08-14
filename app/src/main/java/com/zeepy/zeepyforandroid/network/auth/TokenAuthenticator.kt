package com.zeepy.zeepyforandroid.network.auth

import android.util.Log
import com.kakao.sdk.network.ApiFactory
import com.zeepy.zeepyforandroid.BuildConfig
import com.zeepy.zeepyforandroid.di.NetworkModule
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val authApiService: ZeepyApiService,
    private val userPreferenceManager: UserPreferenceManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            Log.e("unauth", userPreferenceManager.fetchUserAccessToken())
            return response.request.newBuilder().addHeader("X-AUTH-TOKEN", getNewToken().toString()).build()

//            val updatedToken = getNewToken()
//            updatedToken?.let {
//                return response.request.newBuilder().header("X-AUTH-TOKEN", it)
//                    .build()
//            }

        } else {
//            return null
            Log.e("auth", userPreferenceManager.fetchUserAccessToken())

            return response.request.newBuilder().addHeader("X-AUTH-TOKEN", userPreferenceManager.fetchUserAccessToken()).build()
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