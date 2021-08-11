package com.zeepy.zeepyforandroid.community.frame.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CommunityFrameViewModel @Inject constructor(
    private val postingListRepository: PostingListRepository
): BaseViewModel() {
    private val _postingList = MutableLiveData<List<PostingDetailModel>>()
    val postingList: LiveData<List<PostingDetailModel>>
        get() = _postingList

    private val _searchAddressQuery = MutableLiveData<String>()
    val searchAddressQuery: LiveData<String>
        get() = _searchAddressQuery

    private val _detailAddress = MutableLiveData<String>()
    val detailAddress: LiveData<String>
        get() = _detailAddress

    fun changeSearchAddressQuery(address: String) {
        _searchAddressQuery.value = address
    }

    fun changeDetailAddressQuery(detailAddress: String) {
        _detailAddress.value = detailAddress
    }

    @SuppressLint("CheckResult")
    fun getPostingList() {
        addDisposable(
            postingListRepository.getPostingList("", "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _postingList.postValue(it)
                },{
                    it.printStackTrace()
                })
        )
    }
}