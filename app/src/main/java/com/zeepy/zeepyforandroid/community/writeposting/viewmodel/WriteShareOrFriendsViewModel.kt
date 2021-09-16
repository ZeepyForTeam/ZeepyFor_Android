package com.zeepy.zeepyforandroid.community.writeposting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.controller.WritePostingController
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import com.zeepy.zeepyforandroid.enum.PostingType
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

import javax.inject.Inject

@HiltViewModel
class WriteShareOrFriendsViewModel@Inject constructor(
    private val writePostingController: WritePostingController,
    private val zeepyLocalRepository: ZeepyLocalRepository
) : BaseViewModel() {
    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    private val _postingType = MutableLiveData<String>()
    val postingType: LiveData<String>
        get() = _postingType

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String>
        get() = _selectedAddress

    private val _isEveryInfoEntered = MutableLiveData<Boolean>()
    val isEveryInfoEntered: LiveData<Boolean>
        get() = _isEveryInfoEntered

    private val _uploadImages = MutableLiveData<List<String>>(listOf())
    val uploadImages: LiveData<List<String>>
        get() = _uploadImages

    private val _requestWritePosting = MutableLiveData<RequestWritePosting>()
    val requestWritePosting: LiveData<RequestWritePosting>
        get() = _requestWritePosting

    init {
        fetchAddressList()
    }

    fun changePostingType(type: String) {
        _postingType.value = type
    }

    fun checkInputEveryField(): Boolean {
        val fieldList = listOf(title.value, content.value)
        return fieldList.none { it.isNullOrEmpty() }
    }

    fun fetchAddressList() {
        addDisposable(
            zeepyLocalRepository.fetchAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val selectedAddress = it.filter { it.isAddressCheck }.first().cityDistinct
                    _selectedAddress.postValue(selectedAddress)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun sendRequestData() {
        val requestData = RequestWritePosting(
            selectedAddress.value!!,
            postingType.value!!,
            content.value,
            uploadImages.value!!,
            null,
            null,
            null,
            null,
            null,
            null,
            title.value
        )
        _requestWritePosting.value = requestData
    }
}