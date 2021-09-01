package com.zeepy.zeepyforandroid.community.frame.viewmodel

import android.app.Application
import android.os.Parcelable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.address.datasource.AddressDataSource
import com.zeepy.zeepyforandroid.address.repository.SearchAddressListRepository
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepository
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import com.zeepy.zeepyforandroid.util.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CommunityFrameViewModel @Inject constructor(
    private val addressDataSource: AddressDataSource,
    private val postingListRepository: PostingListRepository,
    private val zeepyLocalRepository: ZeepyLocalRepository,
    private val searchAddressListRepository: SearchAddressListRepository
    ) : BaseViewModel() {
    private val _paginationIdx = MutableLiveData<Int>(0)
    val paginationIdx: LiveData<Int>
        get() = _paginationIdx

    private val _currentFragmentId = MutableLiveData<Int>()
    val currentFragmentId: LiveData<Int>
        get() = _currentFragmentId

    private val _addressList = MutableLiveData<List<LocalAddressEntity>?>(listOf())
    val addressList: LiveData<List<LocalAddressEntity>?>
        get() = _addressList

    private val _selectedFilter = MutableLiveData<String?>(null)
    val selectedFilter: LiveData<String?>
        get() = _selectedFilter

    private val _postingList = MutableLiveData<MutableList<PostingListModel>>(mutableListOf())
    val postingList: LiveData<MutableList<PostingListModel>>
        get() = _postingList

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String>
        get() = _selectedAddress

    private val _resultSearchedAddress = MutableLiveData<List<SearchAddressListModel>>()
    val resultSearchedAddress: LiveData<List<SearchAddressListModel>>
        get() = _resultSearchedAddress

    private val _selectedBuildingId = MutableLiveData<Int>()
    val selectedBuildingId: LiveData<Int>
        get() = _selectedBuildingId

    init {
        getAddressListFromLocal()
    }

    fun changeSelectedBuildingId(id: Int) {
        _selectedBuildingId.value = id
    }

    fun changePaginationIdx(idx: Int) {
        _paginationIdx.value = idx
    }

    fun changeCurrentFragmentId(id: Int) {
        _currentFragmentId.value = id
    }

    fun changeSelectedFilter(filter: String?) {
        _selectedFilter.value = filter
    }

    fun changeSelectedAddress(address: String) {
        _selectedAddress.value = address
    }

    fun addPostingList(newPostings: List<PostingListModel>?) {
        val joinedPostingList: MutableList<PostingListModel> = arrayListOf()
        postingList.value?.let { joinedPostingList.addAll(it.toTypedArray()) }
        newPostings?.let { joinedPostingList.addAll(it) }
        _postingList.postValue(joinedPostingList)
    }

    fun removePostingList() {
        val postings = postingList.value?.toMutableList()
        postings?.clear()
        _postingList.value = postings!!
    }

    fun fetchPostingList() {
        if (paginationIdx.value != -1) {
            when(currentFragmentId.value) {
                0 -> getStoryZipPostingList()
                1 -> getMyZipList()
            }
        }
    }

    private fun getStoryZipPostingList() {
        val type = selectedFilter.value
        addDisposable (
            postingListRepository.getPostingList(selectedAddress.value.toString(), type, paginationIdx.value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isNullOrEmpty()) {
                        _paginationIdx.value = -1
                    } else {
                        addPostingList(it)
                        increasePageIdx()
                    }
                }, {
                    _paginationIdx.value = -1
                    it.printStackTrace()
                })
        )
    }

    private fun getMyZipList() {
        val type = selectedFilter.value
        addDisposable (
            postingListRepository.getMyZipList(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    addPostingList(it)
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun increasePageIdx() {
        var page = paginationIdx.value
        if (page != null) {
            page += 1
            _paginationIdx.value = page!!
        }
    }

    fun getAddressListFromServer() {
        addDisposable (
            addressDataSource.fetchAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (!response.addresses.isNullOrEmpty()) {
                        insertAddressListToLocal(response.addresses.map { it.toLocalAddressEntity() })
                    } else {
                        getAddressListFromLocal()
                    }
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun insertAddressListToLocal(addressList: List<LocalAddressEntity>) {
        addDisposable (
            Observable.fromCallable {
                zeepyLocalRepository.insertAllAddress(addressList)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getAddressListFromLocal()
                }, {
                    getAddressListFromLocal()
                    it.printStackTrace()
                })
        )
    }

    private fun getAddressListFromLocal() {
        addDisposable (
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

    fun searchBuildingAddress(address: String) {
        addDisposable (
            searchAddressListRepository.searchBuildingAddressList(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    _resultSearchedAddress.postValue(response)
                }, {
                    it.printStackTrace()
                })
        )
    }
}