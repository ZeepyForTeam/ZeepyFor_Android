package com.zeepy.zeepyforandroid.review.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.address.AddressEntity
import com.zeepy.zeepyforandroid.address.repository.SearchAddressListRepository
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.address.controller.AddressController
import com.zeepy.zeepyforandroid.address.datasource.AddressDataSource
import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.enum.LessorAge
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.review.PostReviewController
import com.zeepy.zeepyforandroid.review.data.dto.RequestWriteReview
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import com.zeepy.zeepyforandroid.review.data.entity.ReviewSearchAddressModel
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

import javax.inject.Inject

@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val addressController: AddressController,
    private val addressDataSource: AddressDataSource,
    private val postReviewController: PostReviewController,
    private val zeepyLocalRepository: ZeepyLocalRepository,
    private val searchAddressListRepository: SearchAddressListRepository
    ) : BaseViewModel() {
    private val _isCommunityTheme = MutableLiveData<Boolean>(false)
    val isCommunityTheme: LiveData<Boolean>
        get() = _isCommunityTheme

    private val _isJustRegisterAddress = MutableLiveData<Boolean>(false)
    val isJustRegisterAddress: LiveData<Boolean>
        get() = _isJustRegisterAddress

    private val _addressListRegistered = MutableLiveData<MutableList<LocalAddressEntity>>(mutableListOf())
    val addressListRegistered: LiveData<MutableList<LocalAddressEntity>>
        get() = _addressListRegistered

    private val _lessorPersonality = MutableLiveData<String>("")
    val lessorPersonality: LiveData<String>
    get() = _lessorPersonality

    private val _houseListSearched = MutableLiveData<List<ReviewSearchAddressModel>>()
    val houseListSearched: LiveData<List<ReviewSearchAddressModel>>
        get() = _houseListSearched

    private val _fullReviewAddress = MutableLiveData<String>()
    val fullReviewAddress: LiveData<String>
        get() = _fullReviewAddress

    private val _addressSelected = MutableLiveData<LocalAddressEntity>()
    val addressSelected: LiveData<LocalAddressEntity>
        get() = _addressSelected

    private val _selectedBuildingId = MutableLiveData<Int>()
    val selectedBuildingId: LiveData<Int>
        get() = _selectedBuildingId

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

    private val _houseURLImages = MutableLiveData<List<String>>(listOf())
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

    private val _resultSearchedAddress = MutableLiveData<List<SearchAddressListModel>>()
    val resultSearchedAddress: LiveData<List<SearchAddressListModel>>
        get() = _resultSearchedAddress

    val checkedAge = MutableLiveData<Int>()
    val reviewOfLessor = MutableLiveData<String>()
    val detailAddress = MutableLiveData<String>()
    val sexChecked = MutableLiveData<Int>()
    val reviewOfHouse = MutableLiveData<String>()

    init {
        getAddress()
    }

    fun changeIsCommunityTheme(boolean: Boolean) {
        _isCommunityTheme.value = boolean
    }

    fun changeFullReviewAddress(fullAddress: String) {
        _fullReviewAddress.value = fullAddress
    }

    fun changeSelectedBuildingId(id: Int) {
        _selectedBuildingId.value = id
    }

    fun changeIsJustRegisterAddress(boolean: Boolean) {
        _isJustRegisterAddress.value = boolean
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

    fun changeLessorPersonality(communityTendency: String) {
        _lessorPersonality.value = communityTendency
    }

    fun checkTotalHouseReviewEmpty(): Boolean {
        return (reviewOfHouse.value.isNullOrEmpty() || houseTotalEvaluation.value.isNullOrEmpty())
    }

    @SuppressLint("CheckResult")
    fun postReviewToServer() {
        postReviewController.postReview(
            RequestWriteReview(
                fullReviewAddress.value!!,
                selectedBuildingId.value!!,
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
                houseTotalEvaluation.value!!,
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
            zeepyLocalRepository.fetchAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _addressListRegistered.postValue(it as MutableList<LocalAddressEntity>)
                }, {
                    it.printStackTrace()
                })
        )
    }

    fun deleteAddress(address: LocalAddressEntity) {
        val addresses = addressListRegistered.value
        addresses?.remove(address)
        _addressListRegistered.value = addresses!!

        val addressDTO = addressListRegistered.value?.map {
            AddressEntity(it.cityDistinct, it.isAddressCheck, it.primaryAddress)
        }

        val requestAddresses = addressDTO?.let {
            AddressListDTO(it)
        }

        if (addressDTO != null) {
            addDisposable(
                addressController.addAddress(requestAddresses!!)
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

    fun addAddress(address: AddressEntity) {
        _addressListRegistered.value?.add(address.toLocalAddressEntity())

        val requestAddress = _addressListRegistered.value?.map {
            it.toAddressListDTO()
        }?.let {
            AddressListDTO(
                it
            )
        }

        addDisposable(
            addressController.addAddress(requestAddress!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    insertAddressToLocal(address.toLocalAddressEntity())
                    Log.e("insert all", "insert all")
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun deleteAddressFromLocal(address: LocalAddressEntity) {
        addDisposable(
            Observable.fromCallable{
                zeepyLocalRepository.deleteAddress(address)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    getAddress()
                    Log.e("delete success", "yes!!")
                }, {
                    it.printStackTrace()
                })
        )
    }

    private fun insertAddressToLocal(address: LocalAddressEntity) {
        addDisposable(
            Observable.fromCallable{
                zeepyLocalRepository.insertAddress(address)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e("insert success", "yes!!")
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

    fun getBuildingId() {
        addDisposable(
            addressDataSource.fetchBuildgingInfoByAddress(
                "${addressSelected.value?.cityDistinct} ${addressSelected.value?.primaryAddress}"
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _selectedBuildingId.postValue(it.id)
                }, {
                    it.printStackTrace()
                })
        )
    }
}