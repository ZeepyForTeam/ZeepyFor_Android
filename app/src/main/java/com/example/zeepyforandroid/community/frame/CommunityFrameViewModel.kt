package com.example.zeepyforandroid.community.frame

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeepyforandroid.community.data.entity.PostingModel
import com.example.zeepyforandroid.community.data.repository.PostingListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CommunityFrameViewModel @Inject constructor(
    private val postingListRepository: PostingListRepository
): ViewModel() {
    private val _postingList = MutableLiveData<List<PostingModel>>()
    val postingList: LiveData<List<PostingModel>>
        get() = _postingList

    @SuppressLint("CheckResult")
    fun getPostingList() {
        postingListRepository.getPostingList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _postingList.postValue(it)
                Log.e("list ", it.toString())
            },{
                it.printStackTrace()
            })
    }

}