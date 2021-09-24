package com.zeepy.zeepyforandroid.myprofile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.usecase.GetBuildingsAllUseCase
import com.zeepy.zeepyforandroid.map.usecase.GetBuildingsUserLikeUseCase
import com.zeepy.zeepyforandroid.map.usecase.util.Result
import com.zeepy.zeepyforandroid.map.usecase.util.data
import com.zeepy.zeepyforandroid.map.usecase.util.succeeded
import com.zeepy.zeepyforandroid.myprofile.data.SimpleReviewDTOList
import com.zeepy.zeepyforandroid.myprofile.repository.MyProfileRepository
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.signin.controller.UserDataController
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val userDataController: UserDataController,
    private val userPreferenceManager: UserPreferenceManager,
    private val repository: MyProfileRepository,
    private val getBuildingsUserLikeUseCase: GetBuildingsUserLikeUseCase
) : BaseViewModel() {

    private val _isLoggedOut = MutableLiveData<Boolean?>()
    val isLoggedOut: LiveData<Boolean?>
        get() = _isLoggedOut

    private val _isWithdrawn = MutableLiveData<Boolean?>()
    val isWithdrawn: LiveData<Boolean?> = _isWithdrawn

    private val _userReviews = MutableLiveData<SimpleReviewDTOList>()
    val userReviews: LiveData<SimpleReviewDTOList>
        get() = _userReviews

    private val _userWishList = MutableLiveData<Result<List<BuildingSummaryModel>>>()
    val userWishList: LiveData<Result<List<BuildingSummaryModel>>>
        get() = _userWishList

    init {
        _isWithdrawn.value = false
        _isLoggedOut.value = !userPreferenceManager.fetchIsAlreadyLogin()
    }

    init {
        getUserReviews()
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

    fun getUserReviews() {
        viewModelScope.launch {
            try {
                val result = repository.getUserReviews()
                _userReviews.value = result
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun getWishList() {
        viewModelScope.launch {
            val result = getBuildingsUserLikeUseCase(
                GetBuildingsUserLikeUseCase.Params(
                    0 // FIXME: Iterate Page
                )
            )

            if (result.succeeded) {
                _userWishList.value = result
                Log.e("GET ALL BUILDINGS RESPONSE", "" + result.data)
            }
        }
    }

    fun submitWithdrawal() {
        viewModelScope.launch {
            try {
                val result = repository.submitWithdrawal()
                _isWithdrawn.value = true
            } catch (e: Throwable) {
                e.printStackTrace()
            }
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