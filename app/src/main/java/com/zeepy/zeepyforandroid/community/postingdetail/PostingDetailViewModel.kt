package com.zeepy.zeepyforandroid.community.postingdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeepy.zeepyforandroid.community.data.entity.CommentModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePosting
import com.zeepy.zeepyforandroid.preferences.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostingDetailViewModel @Inject constructor(
    private val sharedPrefs: SharedPreferencesManager
): ViewModel() {
    private val _posting = MutableLiveData<PostingDetailModel>()
    val postingDetail: LiveData<PostingDetailModel>
        get() = _posting

    private val _commentList = MutableLiveData<List<CommentModel>>()
    val commentList: LiveData<List<CommentModel>>
        get() = _commentList

    val commentWriting = MutableLiveData<String>()

    private val currentList = mutableListOf<CommentModel>()

    val isSecretCommentWriting = MutableLiveData<Boolean>()

    private val _isGroupPurchase = MutableLiveData<Boolean>(false)
    val isGroupPurchase: LiveData<Boolean>
        get() = _isGroupPurchase

    private val _hasAchievement = MutableLiveData<Boolean>(false)
    val hasAchievement: LiveData<Boolean>
        get() = _hasAchievement



    fun changePosting(postingDetailSelected: PostingDetailModel) {
        _posting.value = postingDetailSelected
    }

    fun changeIsGroupPurchase() {
        _isGroupPurchase.value = ResponsePosting.PostingType.JOINTPURCHASE.tag == postingDetail.value?.typePosting
    }

    fun changeCommentList(comments: MutableList<CommentModel>?) {
        if(!comments.isNullOrEmpty()){
            _commentList.value = comments!!
            currentList.addAll(comments)
        }
    }

    fun postComment() {
        if (!commentWriting.value.isNullOrEmpty()) {
            currentList.add(
                CommentModel(
                    sharedPrefs.getSharedPrefs("userIdx", -1),
                    "https://github.com/SONPYEONGHWA.png",
                    "hello",
                    commentWriting.value.toString(),
                    "",
                    isSecretCommentWriting.value ?: false,
                    null
                )
            )
            _commentList.postValue(currentList)
        }
    }
}