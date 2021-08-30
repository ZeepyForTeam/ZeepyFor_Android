package com.zeepy.zeepyforandroid.community.frame.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nhn.android.idp.common.connection.NetworkState
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.address.datasource.AddressDataSource
import com.zeepy.zeepyforandroid.address.repository.SearchAddressListRepository
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepository
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import com.zeepy.zeepyforandroid.util.NetworkStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CommunityFrameViewModel @Inject constructor(
    private val addressDataSource: AddressDataSource,
    private val postingListRepository: PostingListRepository,
    private val zeepyLocalRepository: ZeepyLocalRepository,
    private val searchAddressListRepository: SearchAddressListRepository
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

    private val _postingList = MutableLiveData<NetworkStatus<List<PostingListModel>>>()
    val postingList: LiveData<NetworkStatus<List<PostingListModel>>>
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

    fun changeSelectedBuildingId(id: Int) {
        _selectedBuildingId.value = id
    }

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

    private fun getStoryZipPostingList() {
        val type = selectedCategory.value
        _postingList.value = NetworkStatus.LOADING(null)
        addDisposable(
            postingListRepository.getPostingList(selectedAddress.value.toString(), type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _postingList.postValue(NetworkStatus.SUCCESS(it))
                }, {
                    _postingList.postValue(NetworkStatus.ERROR(null, it.message.toString()))
                    it.printStackTrace()
                })
        )
    }

    private fun getMyZipList() {
        val type = selectedCategory.value
        _postingList.value = NetworkStatus.LOADING(null)
        addDisposable(
            postingListRepository.getMyZipList(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _postingList.postValue(NetworkStatus.SUCCESS(it))
                }, {
                    _postingList.postValue(NetworkStatus.ERROR(null, it.message.toString()))
                    it.printStackTrace()
                })
        )
    }

    fun getAddressListFromServer() {
        addDisposable(
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
        addDisposable(
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

    fun searchBuildingAddress(address: String) {
        addDisposable(
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