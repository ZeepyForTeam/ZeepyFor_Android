package com.example.zeepyforandroid.community.postingdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeepyforandroid.community.data.entity.PostingModel

class PostingDetailViewModel: ViewModel() {
    private val _posting = MutableLiveData<PostingModel>()
    val posting: LiveData<PostingModel>
        get() = _posting

    fun changePosting(postingSelected: PostingModel) {
        _posting.value = postingSelected
    }
}