package com.zeepy.zeepyforandroid.network.auth

import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection
import javax.inject.Inject

@Deprecated("use TokenAuthenticator")
class AuthInterceptor @Inject constructor(
    private val userPreferenceManager: UserPreferenceManager
    ) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
            .newBuilder()
            .addHeader("token", userPreferenceManager.fetchUserAccessToken())
            .build()

        var response = chain.proceed(request)

        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
  //         fetchToken()
            return chain.proceed(request)
        }

        return chain.proceed(request)
    }

//    private fun fetchToken() {
//        val tokenRefreshed = authApiService.fetchAccessToken(
//            RequestTokenDTO(
//                userPreferenceManager.fetchUserAccessToken(),
//                userPreferenceManager.fetchUserRefreshToken()
//            )
//        )
//
//        if (tokenRefreshed.isSuccessful && tokenRefreshed.body() != null) {
//            userPreferenceManager.saveUserAccessToken(tokenRefreshed.body()!!.accessToken)
//            userPreferenceManager.saveUserRefreshToken(tokenRefreshed.body()!!.refreshToken)
//        }
//    }
}