package com.example.zeepyforandroid.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignUpViewModel: ViewModel() {
    private val _isPasswordMatched = MutableLiveData<Boolean>(false)
    val isPasswordMatched: LiveData<Boolean>
        get() = _isPasswordMatched

    fun changeIsPasswordMatched(isMatched: Boolean) {
        _isPasswordMatched.value = isMatched
    }
}