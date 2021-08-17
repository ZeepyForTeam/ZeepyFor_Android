package com.zeepy.zeepyforandroid.review.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.address.AddressEntity
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.address.controller.AddressController
import com.zeepy.zeepyforandroid.address.datasource.AddressDataSource
import com.zeepy.zeepyforandroid.address.dto.ResponseAddressListDTO
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.eunm.LessorAge
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.review.PostReviewController
import com.zeepy.zeepyforandroid.review.data.dto.RequestWriteReview
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import com.zeepy.zeepyforandroid.review.data.entity.ReviewSearchAddressModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val addressController: AddressController,
    private val addressDataSource: AddressDataSource,
    private val postReviewController: PostReviewController,
    private val zeepyLocalRepository: ZeepyLocalRepository
) : BaseViewModel() {
    private val _addressListRegistered = MutableLiveData<MutableList<LocalAddressEntity>>(mutableListOf())
    val addressListRegistered: LiveData<MutableList<LocalAddressEntity>>
        get() = _addressListRegistered

    private val _thirdDetailAddress = MutableLiveData<String>()
    val thirdDetailAddress: LiveData<String>
        get() = _thirdDetailAddress

    private val _writableAddress = MutableLiveData<Boolean>()
    val writableAddress: LiveData<Boolean>
        get() = _writableAddress

    private val _lessorPersonality = MutableLiveData<String>()
    val lessorPersonality: LiveData<String>
    get() = _lessorPersonality

    private val _houseListSearched = MutableLiveData<List<ReviewSearchAddressModel>>()
    val houseListSearched: LiveData<List<ReviewSearchAddressModel>>
        get() = _houseListSearched

    private val _addressSelected = MutableLiveData<LocalAddressEntity>()
    val addressSelected: LiveData<LocalAddressEntity>
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
        getAddress()
    }

    fun changeThirdDetailAddress(detailAddress: String) {
        _thirdDetailAddress.value = detailAddress
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

    fun changeAddressSelected(address: LocalAddressEntity) {
        _addressSelected.value = address
    }

    fun checkEmptyDetailAddress(): Boolean {
        return detailAddress.value.isNullOrEmpty()
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

    private fun getAddress() {
        addDisposable(
            zeepyLocalRepository.getAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _addressListRegistered.postValue(it as MutableList<LocalAddressEntity>)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun fetchBuildingInfo() {
        addDisposable(
            addressDataSource.fetchBuildgingInfoByAddress(
                "${addressSelected.value!!.cityDistinct} ${addressSelected.value!!.primaryAddress}"
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _writableAddress.postValue(true)
                }, {
                    it.printStackTrace()
                    _writableAddress.postValue(false)
                })
        )
    }

    fun deleteAddress(address: LocalAddressEntity) {
        _addressListRegistered.value?.remove(address)

        val addressDTO = addressListRegistered.value?.map {
            AddressEntity(it.cityDistinct, "", it.primaryAddress)
        }
        val requestAddresses = addressDTO?.let {
            ResponseAddressListDTO(
                it
            )
        }

        if (addressDTO != null) {
            addDisposable(
                addressController.deleteAddress(requestAddresses!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        deleteAddressFromLocal(address)
                    }, {
                        it.printStackTrace()
                    })
            )
        }
    }

    private fun deleteAddressFromLocal(address: LocalAddressEntity) {
        addDisposable(
            Observable.fromCallable{
                zeepyLocalRepository.deleteAddress(address)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("delete success", "yes!!")
                }, {
                    it.printStackTrace()
                    Log.e("delete failed", "fucking!!")

                })
        )
    }
}