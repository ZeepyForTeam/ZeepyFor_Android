package com.zeepy.zeepyforandroid.community.postingdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.data.entity.CommentModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingDetail
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepository
import com.zeepy.zeepyforandroid.preferences.SharedPreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PostingDetailViewModel @Inject constructor(
    private val sharedPrefs: SharedPreferencesManager,
    private val postingListRepository: PostingListRepository
): BaseViewModel() {
    private val _postingId = MutableLiveData<Int>()
    val postingId: LiveData<Int>
        get() = _postingId

    private val _postingDetail = MutableLiveData<PostingDetailModel>()
    val postingDetail: LiveData<PostingDetailModel>
        get() = _postingDetail

    private val _commentList = MutableLiveData<List<CommentModel?>?>()
    val commentList: LiveData<List<CommentModel?>?>
        get() = _commentList

    val commentWriting = MutableLiveData<String>()

    private val currentList = mutableListOf<CommentModel?>()

    val isSecretCommentWriting = MutableLiveData<Boolean>()

    private val _isGroupPurchase = MutableLiveData<Boolean>(false)
    val isGroupPurchase: LiveData<Boolean>
        get() = _isGroupPurchase

    private val _hasAchievement = MutableLiveData<Boolean>(false)
    val hasAchievement: LiveData<Boolean>
        get() = _hasAchievement


    fun changePostingId(id: Int) {
        _postingId.value = id
    }

    fun fetchPostingDetailContent() {
        addDisposable(
            postingListRepository.getPostingDetail(postingId.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                         _postingDetail.postValue(it)
                },{
                    it.printStackTrace()
                })
        )

    }

    fun changeIsGroupPurchase() {
        _isGroupPurchase.value = ResponsePostingDetail.PostingType.JOINTPURCHASE.tag == postingDetail.value?.typePosting
    }

    fun changeCommentList(comments: MutableList<CommentModel?>?) {
        if(!comments.isNullOrEmpty()){
            _commentList.value = comments
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