package com.example.zeepyforandroid.review.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zeepyforandroid.eunm.LessorAge
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

    private val _lessorPersonality = MutableLiveData<String>()
    val lessorPersonality: LiveData<String>
    get() = _lessorPersonality

    private val _houseListSearched = MutableLiveData<List<ReviewSearchAddressModel>>()
    val houseListSearched: LiveData<List<ReviewSearchAddressModel>>
        get() = _houseListSearched

    private val _addressList = MutableLiveData<AddressList>()
    val addressList: LiveData<AddressList>
        get() = _addressList

    private val _addressSelected = MutableLiveData<String>()
    val addressSelected: LiveData<String>
        get() = _addressSelected

    private val _lessorGender = MutableLiveData<String>()
    val lessorGender: LiveData<String>
        get() = _lessorGender

    private val _lessorAge = MutableLiveData<Map<String, Int>>(mapOf(LessorAge.TEN.age to 0))
    val lessorAge: LiveData<Map<String, Int>>
        get() = _lessorAge

    private val _roomType = MutableLiveData<String>()
    val roomType: LiveData<String>
        get() = _roomType

    private val _housePictures = MutableLiveData<List<PictureModel>>()
    val pictures: LiveData<List<PictureModel>>
        get() = _housePictures

    private val _addressSearchQuery = MutableLiveData<String>()
    val addressSearchQuery: LiveData<String>
        get() = _addressSearchQuery

    private val _houseTotalEvaluation = MutableLiveData<String>()
    val houseTotalEvaluation: LiveData<String>
        get() = _houseTotalEvaluation

    val ageArrayList = arrayListOf(10,20,30,40,50,60,70,80,90)

    val checkedAge = MutableLiveData<Int>()
    val reviewOfLessor = MutableLiveData<String>()
    val detailAddress = MutableLiveData<String>()
    val sexChecked = MutableLiveData<Int>()
    val reviewOfHouse = MutableLiveData<String>()

    init {
        setDummyAddress()
    }

    fun changeHouseTotalEvaluation(evaluation: String) {
        _houseTotalEvaluation.value = evaluation
    }

    fun changeRoomType(type: String) {
        _roomType.value = type
    }

    fun changeAddressSearchgQuery(query: String) {
        _addressSearchQuery.value = query
    }

    fun changeLessorGender(gender: String) {
        _lessorGender.value = gender
    }

    fun changeLessorAge(age: Map<String, Int>) {
        _lessorAge.value = age
    }

    fun changeHousePictures(pictures: List<PictureModel>) {
        _housePictures.value = pictures
    }

    fun checkInputEveryLessorInfo(): Boolean {
        return !(reviewOfLessor.value.isNullOrEmpty() || lessorGender.value.isNullOrEmpty())
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

    fun changeLessorPersonality(communityTendency: String) {
        _lessorPersonality.value = communityTendency
    }

    fun checkInputAddressQuery(): Boolean {
        return !addressSearchQuery.value.isNullOrEmpty()
    }

    fun postReviewToServer() {

    }

    //Todo: api 연결하면 더미데이터 지우고 Datasource - Repository pattern으로 바꾸기
    private fun setDummyAddress() {
        val dummy = AddressList()
        dummy.apply {
            add(
                AddressModel(
                    "서울특별시 마포구 망원로 48-1"
                )
            )
            add(
                AddressModel(
                    "서울특별시 강남구 신사동 56-1"
                )
            )
            add(
                AddressModel(
                    "서울특별시 서대문구 연희동 26-8"
                )
            )
        }
        _addressList.value = dummy
    }

    fun deleteAddress(addressModel: AddressModel) {
        _addressList.value?.remove(addressModel)
    }
}