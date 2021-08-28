package com.zeepy.zeepyforandroid.network.auth

import android.util.Log
import com.zeepy.zeepyforandroid.application.DaggerZeepyApplication_HiltComponents_SingletonC.builder
import com.zeepy.zeepyforandroid.network.auth.controller.TokenController
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenController: TokenController,
    private val userPreferenceManager: UserPreferenceManager
) : Interceptor {

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeaders(userPreferenceManager.fetchUserAccessToken()).build()
        val response = chain.proceed(request)
        try {
            if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                val tokenRefreshed = tokenController.fetchAccessToken()

                if (tokenRefreshed.isSuccessful) {
                    userPreferenceManager.saveUserAccessToken(tokenRefreshed.body()!!.accessToken)
                    userPreferenceManager.saveUserRefreshToken(tokenRefreshed.body()!!.refreshToken)
                    val newRequest = chain.request().newBuilder()
                        .addHeaders(userPreferenceManager.fetchUserAccessToken())
                        .build()

                    return chain.proceed(newRequest)
                }
            }
            Log.e("AuthInterceptor catch Excpetion run?", "NO")
            return response
        } catch (e: Exception) {
            Log.e("AuthInterceptor catch Excpetion run?", "YES")
            e.printStackTrace()
            return response
        }
    }

    private fun Request.Builder.addHeaders(token: String) =
        this.apply { header(AUTH_HEADER_KEY, token) }

    companion object {
        private const val AUTH_HEADER_KEY = "X-AUTH-TOKEN"
    }
}
