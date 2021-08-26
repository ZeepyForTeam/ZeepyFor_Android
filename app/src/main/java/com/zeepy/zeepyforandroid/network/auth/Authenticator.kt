package com.zeepy.zeepyforandroid.network.auth

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.dto.RequestTokenDTO
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import javax.inject.Inject

//class Authenticator @Inject constructor(
//    private val zeepyApiService: ZeepyApiService,
//    private val userPreferenceManager: UserPreferenceManager
//): Authenticator {
//    override fun authenticate(route: Route?, response: Response): Request? {
//        val updatedToken = refreshAccessToken()
//        return updatedToken?.let {
//            response.request.newBuilder().header("X-AUTH-TOKEN", it)
//                .build()
//        }
//    }
//
//    private fun refreshAccessToken(): String? {
//        val refreshToken = userPreferenceManager.fetchUserRefreshToken()
//        val accessToken = userPreferenceManager.fetchUserAccessToken()
//        val response = zeepyApiService.fetchAccessToken(
//            RequestTokenDTO(
//                accessToken,
//                refreshToken
//            )
//        )
//        response.body()?.let { userPreferenceManager.saveUserAccessToken(it.accessToken) }
//        response.body()?.let { userPreferenceManager.saveUserRefreshToken(it.refreshToken) }
//
//
//        return response.body()?.accessToken
//    }
//}