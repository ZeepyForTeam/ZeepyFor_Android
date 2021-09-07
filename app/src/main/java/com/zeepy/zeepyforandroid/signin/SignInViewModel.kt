package com.zeepy.zeepyforandroid.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kakao.auth.authorization.accesstoken.AccessToken
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.signin.controller.SignInController
import com.zeepy.zeepyforandroid.signin.dto.request.RequestLoginDTO
import com.zeepy.zeepyforandroid.signin.dto.request.RequestSocialSigninDTO
import com.zeepy.zeepyforandroid.signin.dto.response.ResponseSocialSignInDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInController: SignInController,
    private val userPreferenceManager: UserPreferenceManager
) : BaseViewModel() {
    val email = MutableLiveData<String>("")
    val password = MutableLiveData<String>("")

    private val _accessToken = MutableLiveData<String>()
    val accessToken: LiveData<String>
        get() = _accessToken

    private val _refreshToken = MutableLiveData<String>()
    val refreshToken: LiveData<String>
        get() = _refreshToken

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

    fun changeAccessToken(token: String) {
        _accessToken.value = token
    }

    fun changeRefreshToken(token: String) {
        _refreshToken.value = token
    }

    fun signIn() {
        addDisposable(
            signInController.signin(
                RequestLoginDTO(
                    email.value.toString(),
                    password.value.toString()
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response != null) {
                        successSignIn(response)
                    }
                }, {
                    it.printStackTrace()
                    failedToSignIn()
                })
        )
    }

    fun kakaoLogIn(accessToken: String) {
        addDisposable(
            signInController.kakaoSignin(RequestSocialSigninDTO(accessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    successSignIn(response)
                }, {
                    failedToSignIn()
                    it.printStackTrace()
                })
        )
    }

    fun naverLogIn(accessToken: String) {
        addDisposable(
            signInController.naverSignin(RequestSocialSigninDTO(accessToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    successSignIn(response)
                }, {
                    it.printStackTrace()
                    failedToSignIn()
                })
        )
    }

    private fun failedToSignIn() {
        userPreferenceManager.saveIsAlreadyLogin(false)
        _loginSuccess.postValue(false)
    }

    private fun successSignIn(response: ResponseAuthDTO) {
        _loginSuccess.postValue(true)
        userPreferenceManager.apply {
            saveIsAlreadyLogin(true)
            saveUserAccessToken(response.accessToken)
            saveUserRefreshToken(response.refreshToken)
            saveUserEmail(response.userEmail)
            saveUserId(response.userId)
        }
    }
}