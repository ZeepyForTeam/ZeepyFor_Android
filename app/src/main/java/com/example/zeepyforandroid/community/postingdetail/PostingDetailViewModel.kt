package com.example.zeepyforandroid.community.postingdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeepyforandroid.community.data.entity.PostingModel
import com.example.zeepyforandroid.community.data.remote.response.ResponsePosting

class PostingDetailViewModel: ViewModel() {
    private val _posting = MutableLiveData<PostingModel>()
    val posting: LiveData<PostingModel>
        get() = _posting

    private val _isGroupPurchase = MutableLiveData<Boolean>(false)
    val isGroupPurchase: LiveData<Boolean>
        get() = _isGroupPurchase

    private val _hasAchievement = MutableLiveData<Boolean>(false)
    val hasAchievement: LiveData<Boolean>
        get() = _hasAchievement

    fun changePosting(postingSelected: PostingModel) {
        _posting.value = postingSelected
    }

    fun changeIsGroupPurchase() {
        _isGroupPurchase.value = ResponsePosting.PostingType.GROUP_PURCHASE.tag == posting.value?.typePosting
    }

}