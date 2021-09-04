package com.zeepy.zeepyforandroid.network.auth

import android.util.Log
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.network.auth.controller.TokenController
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import java.net.HttpURLConnection
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenController: TokenController,
    private val userPreferenceManager: UserPreferenceManager,
    private val zeepyLocalRepository: ZeepyLocalRepository
) : Interceptor {

    @Throws(Exception::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().addHeaders(userPreferenceManager.fetchUserAccessToken())
                .build()

        val response = chain.proceed(request)
        if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {
            response.close()
            val accessToken = refreshAccessToken()
            val newRequest = chain.request().newBuilder()
                .addHeaders(accessToken)
                .build()

            return chain.proceed(newRequest)
        }
        return response
    }

    private fun refreshAccessToken():String {
        val tokenRefreshed = tokenController.fetchAccessToken()
        var accessToken = ""
        tokenRefreshed.enqueue(object : Callback<ResponseAuthDTO> {
            override fun onResponse(
                call: Call<ResponseAuthDTO>,
                response: retrofit2.Response<ResponseAuthDTO>
            ) {
                if(response.isSuccessful) {
                    if (response.body() != null) {
                        userPreferenceManager.saveUserAccessToken(response.body()!!.accessToken)
                        userPreferenceManager.saveUserRefreshToken(response.body()!!.refreshToken)
                        accessToken =  response.body()!!.accessToken
                    } else {
                        deleteUserInfo()
                    }
                } else {
                    deleteUserInfo()
                }
            }

            override fun onFailure(call: Call<ResponseAuthDTO>, t: Throwable) {
                userPreferenceManager.saveIsAlreadyLogin(false)
            }
        })
        return accessToken
    }

    private fun deleteUserInfo() {
        val disposable = CompositeDisposable()
        userPreferenceManager.saveIsAlreadyLogin(false)

        disposable.add(
            Observable.fromCallable{
                zeepyLocalRepository.deleteEveryAddress()
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("delete all address", "success")
                }, {
                    Log.e("delete all address", "fail")
                })
        )
        disposable.clear()
    }

    private fun Request.Builder.addHeaders(token: String) =
        this.apply { header(AUTH_HEADER_KEY, token) }

    companion object {
        private const val AUTH_HEADER_KEY = "X-AUTH-TOKEN"
    }

}
