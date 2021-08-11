package com.zeepy.zeepyforandroid.signin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class SignInViewModel(application: Application): AndroidViewModel(application) {
    private val _accessToken = MutableLiveData<String>()
    val accessToken: LiveData<String>
        get() = _accessToken

    private val _refreshToken = MutableLiveData<String>()
    val refreshToken: LiveData<String>
        get() = _refreshToken


    fun changeAccessToken(token: String) {
        _accessToken.value = token
    }

    fun changeRefreshToken(token: String) {
        _refreshToken.value = token
    }

}