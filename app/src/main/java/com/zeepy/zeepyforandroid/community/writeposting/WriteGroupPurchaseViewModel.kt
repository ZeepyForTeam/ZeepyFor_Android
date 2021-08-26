package com.zeepy.zeepyforandroid.community.writeposting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.community.controller.WritePostingController
import com.zeepy.zeepyforandroid.community.data.entity.CollectContentModel
import com.zeepy.zeepyforandroid.community.data.remote.requestDTO.RequestWritePosting
import com.zeepy.zeepyforandroid.enum.PostingType
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class WriteGroupPurchaseViewModel @Inject constructor(
    private val writePostingController: WritePostingController,
    private val zeepyLocalRepository: ZeepyLocalRepository
) : BaseViewModel() {
    val title = MutableLiveData<String>()
    val productName = MutableLiveData<String>()
    val productPrice = MutableLiveData<String>()
    val purchaseSite = MutableLiveData<String>()
    val sharingMethod = MutableLiveData<String>()
    val content = MutableLiveData<String>()
    val targetCount = MutableLiveData<String>()
    private val _instructions = MutableLiveData<String>()
    val instructions: LiveData<String>
        get() = _instructions

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String>
        get() = _selectedAddress

    private val _collectList = MutableLiveData<MutableList<CollectContentModel>?>(mutableListOf())
    val collectList: LiveData<MutableList<CollectContentModel>?>
        get() = _collectList

    private val _isEveryInfoEntered = MutableLiveData<Boolean>()
    val isEveryInfoEntered: LiveData<Boolean>
        get() = _isEveryInfoEntered

    private val _uploadImages = MutableLiveData<List<String>>(listOf())
    val uploadImages: LiveData<List<String>>
        get() = _uploadImages

    init {
        fetchAddressList()
    }

    fun makeInstructionString() {
        _instructions.value =
            collectList.value?.joinToString("/") { it.content }
    }

    fun checkEveryInfoEntered() {
        val inputList = listOf(title, productName, productPrice, purchaseSite, targetCount, sharingMethod, content, targetCount)
        val emptyList = inputList.filter { it.value.isNullOrEmpty() }
        _isEveryInfoEntered.value = emptyList.isEmpty()
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

    fun addCollectList(item: CollectContentModel) {
        val collectList = collectList.value
        collectList?.add(item)
        _collectList.value = collectList
    }

    fun removeCollectList(item: CollectContentModel) {
        val collectList = collectList.value
        collectList?.remove(item)
        _collectList.value = collectList
    }

    fun uploadPosting() {
        addDisposable(
            writePostingController.uploadPosting(
                RequestWritePosting(
                    selectedAddress.value!!,
                    PostingType.JOINTPURCHASE.name,
                    content.value,
                    uploadImages.value!!,
                    instructions.value,
                    productName.value,
                    productPrice.value,
                    purchaseSite.value,
                    sharingMethod.value,
                    targetCount.value?.toInt(),
                    title.value
                )
            ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                           Log.e("success posting", "success")
                }, {
                    Log.e("fail posting", "fail")
                })
        )
    }
}