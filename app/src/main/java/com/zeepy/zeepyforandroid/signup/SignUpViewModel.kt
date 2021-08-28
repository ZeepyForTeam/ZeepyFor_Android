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
    val password = MutableLiveData<String>("")
    val passwordCheck = MutableLiveData<String>("")
    val name = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val passwordUnderLength = MutableLiveData<Boolean>()
    val termsApprove = MutableLiveData<Boolean>(false)
    val personalInfoApprove = MutableLiveData<Boolean>(false)

    private val _isPasswordMatched = MutableLiveData<Boolean>(false)
    val isPasswordMatched: LiveData<Boolean>
        get() = _isPasswordMatched

    private val _signUpSuccess = MutableLiveData<Boolean>(false)
    val signUpSuccess: LiveData<Boolean>
        get() = _signUpSuccess

    private val _isEmailRepetition = MutableLiveData<Boolean>(true)
    val isEmailRepetition: LiveData<Boolean>
        get() = _isEmailRepetition

    private val _isNickNameRepetition = MutableLiveData<Boolean>(true)
    val isNickNameRepetition: LiveData<Boolean>
        get() = _isNickNameRepetition

    private val _isInputEverything = MutableLiveData<Boolean>(false)
    val isInputEverythig: LiveData<Boolean>
        get() = _isInputEverything

    fun changeTrueNickNameRepetition() {
        _isNickNameRepetition.value = true
    }

    fun changeTrueEmailRepetition() {
        _isEmailRepetition.value = true
    }

    fun checkEveryInputEntered() {
        _isInputEverything.value =
            (isPasswordMatched.value == true &&
                    isEmailRepetition.value == false &&
                    isNickNameRepetition.value == false &&
                    termsApprove.value == true &&
                    personalInfoApprove.value == true &&
                    !password.value.isNullOrEmpty())
    }

    fun checkPasswordCheckMatched() {
        _isPasswordMatched.value =
            password.value == passwordCheck.value
    }

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
                           _signUpSuccess.postValue(true)
                }, {
                    _signUpSuccess.postValue(false)
                    it.printStackTrace()
                })
        )
    }
}