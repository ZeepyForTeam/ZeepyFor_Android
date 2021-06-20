package com.example.zeepyforandroid.community.postingdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeepyforandroid.community.data.entity.PostingModel
import com.example.zeepyforandroid.community.data.remote.response.ResponsePosting
import com.example.zeepyforandroid.util.SharedUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import org.w3c.dom.Comment
import javax.inject.Inject

@HiltViewModel
class PostingDetailViewModel @Inject constructor(
    private val sharedPrefs: SharedUtil
): ViewModel() {

    private val _posting = MutableLiveData<PostingModel>()
    val posting: LiveData<PostingModel>
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



    fun changePosting(postingSelected: PostingModel) {
        _posting.value = postingSelected
    }

    fun changeIsGroupPurchase() {
        _isGroupPurchase.value = ResponsePosting.PostingType.GROUP_PURCHASE.tag == posting.value?.typePosting
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