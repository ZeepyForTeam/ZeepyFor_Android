package com.example.zeepyforandroid.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeepyforandroid.review.data.entity.AddressList
import com.example.zeepyforandroid.review.data.entity.AddressModel
import com.example.zeepyforandroid.review.data.entity.PictureModel
import com.example.zeepyforandroid.review.data.entity.ReviewSearchAddressModel
import com.example.zeepyforandroid.util.ReviewNotice
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteReviewViewModel @Inject constructor() : ViewModel() {
    private val _currentFragment = MutableLiveData<ReviewNotice>()
    val currentFragment: LiveData<ReviewNotice>
        get() = _currentFragment

    private val _houseListSearched = MutableLiveData<List<ReviewSearchAddressModel>>()
    val houseListSearched: LiveData<List<ReviewSearchAddressModel>>
        get() = _houseListSearched

    private val _addressList = MutableLiveData<AddressList>()
    val addressList: LiveData<AddressList>
        get() = _addressList

    private val _addressSelected = MutableLiveData<String>()
    val addressSelected: LiveData<String>
        get() = _addressSelected

    private val _housePictures = MutableLiveData<List<PictureModel>>()
    val pictures: LiveData<List<PictureModel>>
        get() = _housePictures

    val reviewOfLessor = MutableLiveData<String>()

    val detailAddress = MutableLiveData<String>()
    private val _addressSearchQuery = MutableLiveData<String>()
    val addressSearchQuery: LiveData<String>
        get() = _addressSearchQuery

    val sexChecked = MutableLiveData<Int>()

    init {
        setDummyAddress()
    }

    fun changeAddressSearchgQuery(query: String) {
        _addressSearchQuery.value = query
    }

    fun changeHousePictures(pictures: List<PictureModel>) {
        _housePictures.value = pictures
    }

    fun checkReviewOfLessor(): Boolean {
        return !reviewOfLessor.value.isNullOrEmpty()
    }

    fun changeAddressSelected(address: String) {
        _addressSelected.value = address
    }

    fun checkEmptyDetailAddress(): Boolean {
        return detailAddress.value.isNullOrEmpty()
    }

    fun changeCurrentFragment(reviewNotice: ReviewNotice) {
        _currentFragment.value = reviewNotice
    }

    fun checkInputAddressQuery(): Boolean {
        return !addressSearchQuery.value.isNullOrEmpty()
    }

    //Todo: api ???????????? ??????????????? ????????? Datasource - Repository pattern?????? ?????????
    private fun setDummyAddress() {
        val dummy = AddressList()
        dummy.apply {
            add(
                AddressModel(
                    "??????????????? ????????? ????????? 48-1"
                )
            )
            add(
                AddressModel(
                    "??????????????? ????????? ????????? 56-1"
                )
            )
            add(
                AddressModel(
                    "??????????????? ???????????? ????????? 26-8"
                )
            )
        }
        _addressList.value = dummy
    }

    fun deleteAddress(addressModel: AddressModel) {
        _addressList.value?.remove(addressModel)
    }
}