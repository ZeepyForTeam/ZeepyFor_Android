package com.zeepy.zeepyforandroid.review.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeepy.zeepyforandroid.eunm.LessorAge
import com.zeepy.zeepyforandroid.review.PostReviewController
import com.zeepy.zeepyforandroid.review.data.dto.RequestWriteReview
import com.zeepy.zeepyforandroid.review.data.entity.AddressList
import com.zeepy.zeepyforandroid.review.data.entity.AddressModel
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import com.zeepy.zeepyforandroid.review.data.entity.ReviewSearchAddressModel
import com.zeepy.zeepyforandroid.util.ReviewNotice
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val postReviewController: PostReviewController
) : ViewModel() {
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

    private val _lessorAge = MutableLiveData(mapOf(LessorAge.TEN.age to 0))
    val lessorAge: LiveData<Map<String, Int>>
        get() = _lessorAge

    private val _roomType = MutableLiveData<String>()
    val roomType: LiveData<String>
        get() = _roomType

    private val _houseBitmapImages = MutableLiveData<List<PictureModel>>()
    val bitmapImages: LiveData<List<PictureModel>>
        get() = _houseBitmapImages

    private val _houseURLImages = MutableLiveData<List<String>>()
    val houseURLImages: LiveData<List<String>>
        get() = _houseURLImages

    private val _addressSearchQuery = MutableLiveData<String>()
    val addressSearchQuery: LiveData<String>
        get() = _addressSearchQuery

    private val _houseTotalEvaluation = MutableLiveData<String>()
    val houseTotalEvaluation: LiveData<String>
        get() = _houseTotalEvaluation

    private val _reviewPreference = MutableLiveData(mutableMapOf<String, String>())
    val reviewPreference: LiveData<MutableMap<String, String>>
        get() = _reviewPreference

    private val _selectedOptionList = MutableLiveData<MutableList<String>>(mutableListOf())
    val selectedOptionList: LiveData<MutableList<String>>
        get() = _selectedOptionList

    val checkedAge = MutableLiveData<Int>()
    val reviewOfLessor = MutableLiveData<String>()
    val detailAddress = MutableLiveData<String>()
    val sexChecked = MutableLiveData<Int>()
    val reviewOfHouse = MutableLiveData<String>()

    init {
        setDummyAddress()
    }

    fun selectOption(option: String){
        _selectedOptionList.value?.add(option)
    }

    fun unselectOption(option: String) {
        _selectedOptionList.value?.remove(option)
    }

    fun addReviewPreference(key: String, value: String) {
        _reviewPreference.value?.put(key, value)
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
        _houseBitmapImages.value = pictures
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

    fun checkTotalHouseReviewEmpty(): Boolean {
        return (reviewOfHouse.value.isNullOrEmpty() || houseTotalEvaluation.value.isNullOrEmpty())
    }

    //Todo: 유저 api 및 주소, 빌딩id api 연결되면 수정
    @SuppressLint("CheckResult")
    fun postReviewToServer() {
        postReviewController.postReview(
            RequestWriteReview(
                "",
                313,
                lessorPersonality.value!!,
                selectedOptionList.value!!,
                houseURLImages.value!!,
                lessorAge.value!!.keys.first(),
                lessorGender.value!!,
                reviewOfLessor.value!!,
                reviewPreference.value!!["lightning"]!!,
                reviewPreference.value!!["pest"]!!,
                reviewOfHouse.value!!,
                roomType.value!!,
                reviewPreference.value!!["soundInsulation"]!!,
                reviewPreference.value!!["totalEvaluation"]!!,
                0,
                reviewPreference.value!!["waterPressure"]!!,
            )
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
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