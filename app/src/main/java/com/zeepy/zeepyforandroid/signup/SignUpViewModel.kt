package com.zeepy.zeepyforandroid.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeepy.zeepyforandroid.signup.controller.SignUpController
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpController: SignUpController
) : ViewModel() {
    private val _isPasswordMatched = MutableLiveData<Boolean>(false)
    val isPasswordMatched: LiveData<Boolean>
        get() = _isPasswordMatched

    val password = MutableLiveData<String>("")
    val passwordCheck = MutableLiveData<String>("")
    val name = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val passwordUnderLength = MutableLiveData<Boolean>()

    private val _isEmailRepetition = MutableLiveData<Boolean>()
    val isEmailRepetition: LiveData<Boolean>
        get() = _isEmailRepetition

    private val _isNickNameRepetition = MutableLiveData<Boolean>()
    val isNickNameRepetition: LiveData<Boolean>
        get() = _isNickNameRepetition


    fun checkEmailRepetition() {
        email.value?.let { email ->
            signUpController.checkEmailRepetition(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("email", "success")
                           _isEmailRepetition.postValue(false)
                }, {
                    Log.e("email", "fail")
                    _isEmailRepetition.postValue(true)
                })
        }
    }

    fun checkNickNameRepetition() {
        nickname.value?.let { nickname ->
            signUpController.checkNickNamRepetition(nickname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("nickname", "success")
                    _isNickNameRepetition.postValue(false)

                }, {
                    Log.e("nickname", "fail")
                    _isNickNameRepetition.postValue(true)
                })
        }
    }


}