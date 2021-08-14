package com.zeepy.zeepyforandroid.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.address.AddressDataSource
import com.zeepy.zeepyforandroid.address.ResponseAddressDTO
import com.zeepy.zeepyforandroid.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addressDataSource: AddressDataSource
): BaseViewModel() {

    private val _addressList = MutableLiveData<List<ResponseAddressDTO>>()
    val addressList: LiveData<List<ResponseAddressDTO>>
        get() = _addressList

    fun getAddressList() {
        addDisposable(
            addressDataSource.fetchAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                           Log.e("success", "success")
                }, {

                })
        )

    }
}