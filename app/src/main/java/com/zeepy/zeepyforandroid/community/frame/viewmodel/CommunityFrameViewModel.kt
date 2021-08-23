package com.zeepy.zeepyforandroid.community.frame.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepository
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CommunityFrameViewModel @Inject constructor(
    private val postingListRepository: PostingListRepository,
    private val zeepyLocalRepository: ZeepyLocalRepository
) : BaseViewModel() {
    private val _currentFragmentId = MutableLiveData<Int>()
    val currentFragmentId: LiveData<Int>
        get() = _currentFragmentId

    private val _addressList = MutableLiveData<MutableList<LocalAddressEntity>>(mutableListOf())
    val addressList: LiveData<MutableList<LocalAddressEntity>>
        get() = _addressList

    private val _selectedCategory = MutableLiveData<String?>()
    val selectedCategory: LiveData<String?>
        get() = _selectedCategory

    private val _postingList = MutableLiveData<List<PostingListModel>>()
    val postingList: LiveData<List<PostingListModel>>
        get() = _postingList

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String>
        get() = _selectedAddress

    init {
        getAddressListFromLocal()
    }


    fun changeCurrentFragmentId(id: Int) {
        _currentFragmentId.value = id
    }

    fun changeCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun changeSelectedAddress(address: String) {
        _selectedAddress.value = address
    }

    fun fetchPostingList() {
        when(currentFragmentId.value) {
            0 -> getStoryZipPostingList()
            1 -> getMyZipList()
        }
    }

    fun getStoryZipPostingList() {
        val type = selectedCategory.value
        Log.e("dsfksdj;", selectedAddress.value.toString())
        addDisposable(
            postingListRepository.getPostingList(selectedAddress.value.toString(), type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _postingList.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getMyZipList() {
        val type = selectedCategory.value
        addDisposable(
            postingListRepository.getMyZipList(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _postingList.postValue(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun getAddressListFromLocal() {
        addDisposable(
            zeepyLocalRepository.fetchAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _addressList.postValue(response.toMutableList())
                    if (!response.isNullOrEmpty()) {
                        _selectedAddress.postValue(response.filter { it.isAddressCheck }.first()?.cityDistinct.toString())
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }
}