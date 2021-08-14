package com.zeepy.zeepyforandroid.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.address.AddressDataSource
import com.zeepy.zeepyforandroid.address.AddressEntity
import com.zeepy.zeepyforandroid.address.ResponseAddressListDTO
import com.zeepy.zeepyforandroid.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addressDataSource: AddressDataSource
) : BaseViewModel() {
    private val _addressList = MutableLiveData<ResponseAddressListDTO>()
    val addressList: LiveData<ResponseAddressListDTO>
        get() = _addressList

    private val _selectedAddress = MutableLiveData<String>()
    val selectedAddress: LiveData<String>
        get() = _selectedAddress

    fun getAddressList() {
        addDisposable(
            addressDataSource.fetchAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _addressList.postValue(it)
                    _selectedAddress.postValue(it.addresses.first().cityDistinct)
                }, {
                    it.printStackTrace()
                })
        )
    }
}