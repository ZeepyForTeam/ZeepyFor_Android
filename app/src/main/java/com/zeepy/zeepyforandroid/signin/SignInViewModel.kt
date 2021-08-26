package com.zeepy.zeepyforandroid.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.signin.controller.SignInController
import com.zeepy.zeepyforandroid.signin.dto.RequestLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInController: SignInController,
    private val userPreferenceManager: UserPreferenceManager
): BaseViewModel() {
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
                RequestLogin(
                    email.value.toString(),
                    password.value.toString()
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response != null) {
                        userPreferenceManager.run {
                            _loginSuccess.postValue(true)
                            saveIsAlreadyLogin(true)
                            saveUserAccessToken(response.accessToken)
                            saveUserRefreshToken(response.refreshToken)
                            saveUserId(response.userId)

                            Log.e("login access", "${response.accessToken}")
                            Log.e("login refresh", "${response.refreshToken}")

                        }
                    }
                }, {
                    it.printStackTrace()
                    userPreferenceManager.run {
                        _loginSuccess.postValue(false)
                        saveIsAlreadyLogin(false)
                    }
                })
        )
    }
}