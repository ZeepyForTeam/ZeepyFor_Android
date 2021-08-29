package com.zeepy.zeepyforandroid.community.postingdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.controller.WritePostingController
import com.zeepy.zeepyforandroid.community.data.entity.CommentModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestParticipationDTO
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWriteCommentDTO
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.ResponsePostingDetail
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepository
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.util.DateParser.getCurrentDateComment
import com.zeepy.zeepyforandroid.util.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PostingDetailViewModel @Inject constructor(
    private val writePostingController: WritePostingController,
    private val userPreferenceManager: UserPreferenceManager,
    private val postingListRepository: PostingListRepository
): BaseViewModel() {
    private val currentList = mutableListOf<CommentModel?>()
    val commentWriting = MutableLiveData<String>()
    val isSecretCommentWriting = MutableLiveData<Boolean>()

    private val _isDeletedPosting = MutableLiveData<Boolean>()
    val isDeletedPosting: LiveData<Boolean>
        get() = _isDeletedPosting

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId

    private val _postingId = MutableLiveData<Int>()
    val postingId: LiveData<Int>
        get() = _postingId

    private val _postingDetail = MutableLiveData<NetworkStatus<PostingDetailModel>>()
    val postingDetail: LiveData<NetworkStatus<PostingDetailModel>>
        get() = _postingDetail

    private val _achievementRate = MutableLiveData<Int>()
    val achievementRate: LiveData<Int>
        get() = _achievementRate

    private val _commentList = MutableLiveData<List<CommentModel?>?>()
    val commentList: LiveData<List<CommentModel?>?>
        get() = _commentList

    private val _superCommentId = MutableLiveData<Int?>()
    val superCommentId: LiveData<Int?>
        get() = _superCommentId

    private val _isGroupPurchase = MutableLiveData<Boolean>(false)
    val isGroupPurchase: LiveData<Boolean>
        get() = _isGroupPurchase

    private val _participationComment = MutableLiveData<String>()
    val participationComment: LiveData<String>
        get() = _participationComment

    init {
        _userId.value = userPreferenceManager.fetchUserId()
    }

    fun changeParticipationComment(comment: String) {
        _participationComment.value = comment
    }

    fun changeSuperCommentId(id: Int?) {
        _superCommentId.value = id
    }

    fun changeAchievement(rate: Int) {
        _achievementRate.value = rate
    }

    fun changePostingId(id: Int) {
        _postingId.value = id
    }

    fun fetchPostingDetailContent() {
        _postingDetail.value = NetworkStatus.LOADING()
        addDisposable(
            postingListRepository.getPostingDetail(postingId.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _postingDetail.postValue(NetworkStatus.SUCCESS(it))
                },{
                    _postingDetail.postValue(NetworkStatus.ERROR(null, it.message.toString()))
                    it.printStackTrace()
                })
        )
    }

    fun changeIsGroupPurchase() {
        _isGroupPurchase.value =
            ResponsePostingDetail.PostingType.JOINTPURCHASE.tag == postingDetail.value?.data?.typePosting
    }

    fun changeCommentList(comments: MutableList<CommentModel?>?) {
        if(!comments.isNullOrEmpty()){
            _commentList.value = comments
            currentList.addAll(comments)
        }
    }

    fun postCommentToServer() {
        if (!commentWriting.value.isNullOrEmpty()) {
            addDisposable(
                writePostingController.writeComment(
                    postingId.value!!,
                    RequestWriteCommentDTO(
                        commentWriting.value!!,
                        isSecretCommentWriting.value ?: false,
                        superCommentId.value
                    )
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        changeSuperCommentId(null)
                        fetchPostingDetailContent()
                    }, {
                        changeSuperCommentId(null)
                        it.printStackTrace()
                    })
            )
        }
    }

    fun participateGroupPurchase() {
        val requestParticipation = RequestParticipationDTO(
            participationComment.value.toString(),
            true
        )

        addDisposable(
            writePostingController.participateGroupPurchase(postingId.value!!, requestParticipation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fetchPostingDetailContent()
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun scrapPosting() {
        addDisposable(
            writePostingController.scrapPosting(postingId.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {
                    it.printStackTrace()
                })
        )
    }

    fun cancelScrapPosting() {
        addDisposable(
            writePostingController.cancelScrapPosting(postingId.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {
                    it.printStackTrace()
                })
        )
    }

    fun cancelParticipation() {
        addDisposable(
            writePostingController.cancelParticipation(postingId.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    fetchPostingDetailContent()
                },{
                    it.printStackTrace()
                })
        )
    }

    fun deletePosting() {
        addDisposable(
            writePostingController.deletePosting(postingId.value!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _isDeletedPosting.postValue(true)
                },{
                    _isDeletedPosting.postValue(false)
                    it.printStackTrace()
                })
        )
    }
}