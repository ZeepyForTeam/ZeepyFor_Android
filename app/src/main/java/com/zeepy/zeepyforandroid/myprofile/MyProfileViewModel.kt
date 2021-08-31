package com.zeepy.zeepyforandroid.myprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.map.usecase.util.data
import com.zeepy.zeepyforandroid.map.usecase.util.succeeded
import com.zeepy.zeepyforandroid.map.usecase.util.updateOnSuccessFromUnitResponse
import com.zeepy.zeepyforandroid.myprofile.repository.MyProfileRepository
import com.zeepy.zeepyforandroid.myprofile.usecase.LogoutUseCase
import com.zeepy.zeepyforandroid.myprofile.usecase.WithdrawUseCase
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.signin.controller.UserDataController
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val userDataController: UserDataController,
    private val userPreferenceManager: UserPreferenceManager,
    private val repository: MyProfileRepository,
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    private val _isLoggedOut = MutableLiveData<Boolean?>()
    val isLoggedOut: LiveData<Boolean?>
        get() = _isLoggedOut

    private val _isWithdrawn = MutableLiveData<Boolean?>()
    val isWithdrawn: LiveData<Boolean?> = _isWithdrawn

    init {
        _isWithdrawn.value = false
        _isLoggedOut.value = !userPreferenceManager.fetchIsAlreadyLogin()
    }

    fun getUserNicknameAndEmail(email: String) {
        addDisposable(
            userDataController.getNicknameAndEmail(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    userPreferenceManager.saveUserNickname(response.nickname)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun submitWithdrawal() {
        viewModelScope.launch {
            val result = repository.submitWithdrawal()
            Log.e("result", "" + result)
        }
    }

    fun submitLogout() {
        viewModelScope.launch {
            try {
                val result = repository.logout()
                _isLoggedOut.value = true
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }
}