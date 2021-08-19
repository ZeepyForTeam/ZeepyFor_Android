package com.zeepy.zeepyforandroid.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.address.datasource.AddressDataSource
import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import com.zeepy.zeepyforandroid.base.BaseViewModel
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addressDataSource: AddressDataSource,
    private val zeepyLocalRepository: ZeepyLocalRepository
) : BaseViewModel() {
    private val _addressList = MutableLiveData<AddressListDTO>()
    val addressList: LiveData<AddressListDTO>
        get() = _addressList

    private val _selectedAddress = MutableLiveData<String>("")
    val selectedAddress: LiveData<String>
        get() = _selectedAddress

    init {
        getAddressList()
    }

    fun getAddressList() {
        addDisposable(
            addressDataSource.fetchAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (!response.addresses.isNullOrEmpty()) {
                        insertAddressListToLocal(response.addresses.map { it.toLocalAddressEntity() })
                        _addressList.postValue(response)
                        _selectedAddress.postValue(response.addresses.first().cityDistinct)
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
                           Log.e("success", "success")
                }, {
                    it.printStackTrace()
                    Log.e("fail", "fail")

                })
        )
    }
}