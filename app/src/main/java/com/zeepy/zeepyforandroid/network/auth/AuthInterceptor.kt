package com.zeepy.zeepyforandroid.network.auth

import android.os.ConditionVariable
import android.text.TextUtils
import com.google.gson.Gson
import com.kakao.sdk.auth.network.withAccessToken
import com.zeepy.zeepyforandroid.BuildConfig
import com.zeepy.zeepyforandroid.address.AddressDataSource
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userPreferenceManager: UserPreferenceManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = userPreferenceManager.fetchUserAccessToken()
        val request = chain.request().newBuilder().addHeader("X-AUTH-TOKEN", accessToken).build()
        val response = chain.proceed(request)

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val zeepyApiService = retrofit.create(ZeepyApiService::class.java)

            val tokenRefreshed = zeepyApiService.fetchAccessToken(
                RequestTokenDTO(
                    userPreferenceManager.fetchUserAccessToken(),
                    userPreferenceManager.fetchUserRefreshToken()
                )
            )

            if (tokenRefreshed.isSuccessful) {
                userPreferenceManager.saveUserAccessToken(tokenRefreshed.body()!!.accessToken)
                userPreferenceManager.saveUserRefreshToken(tokenRefreshed.body()!!.refreshToken)
                val newRequest = chain.request().newBuilder()
                    .addHeader("X-AUTH-TOKEN", userPreferenceManager.fetchUserAccessToken())
                    .build()

                return chain.proceed(newRequest)
            }
        }
        return response
    }

    private fun Request.Builder.addHeaders(token: String) =
        this.apply { header(AUTH_HEADER_KEY, token) }

    companion object {
        private const val AUTH_HEADER_KEY = "X-AUTH-TOKEN"
    }
}
