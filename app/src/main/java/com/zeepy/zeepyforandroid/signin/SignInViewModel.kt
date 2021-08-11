package com.zeepy.zeepyforandroid.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
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
                    userPreferenceManager.saveUserAccessToken(response.accessToken)
                    userPreferenceManager.saveUserRefreshToken(response.refreshToken)
                    _loginSuccess.postValue(true)
                }, {
                    it.printStackTrace()
                    _loginSuccess.postValue(false)
                })
        )
    }
}