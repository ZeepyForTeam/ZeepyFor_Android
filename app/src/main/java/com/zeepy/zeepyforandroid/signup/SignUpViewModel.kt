package com.zeepy.zeepyforandroid.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.signup.controller.SignUpController
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpController: SignUpController
) : BaseViewModel() {
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
        addDisposable(
            signUpController.checkEmailRepetition(email.value.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _isEmailRepetition.postValue(false)
                }, {
                    _isEmailRepetition.postValue(true)
                })
        )
    }

    fun checkNickNameRepetition() {
        addDisposable(
            signUpController.checkNickNamRepetition(nickname.value.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _isNickNameRepetition.postValue(false)
                }, {
                    _isNickNameRepetition.postValue(true)
                })
        )
    }

    fun signUp() {
        val requestSignUp = RequestSignUpDTO(
            email.value!!,
            name.value!!,
            nickname.value!!,
            password.value!!,
            true
        )
        addDisposable(
            signUpController.signUp(requestSignUp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {
                    it.printStackTrace()
                })
        )
    }
}