package com.zeepy.zeepyforandroid.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeepy.zeepyforandroid.address.controller.AddressController
import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import com.zeepy.zeepyforandroid.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ChangeAddressViewModel @Inject constructor(
    private val addressController: AddressController
) : BaseViewModel() {
    private val _addressList = MutableLiveData<MutableList<LocalAddressEntity>>(mutableListOf())
    val addressList: LiveData<MutableList<LocalAddressEntity>>
        get() = _addressList

    fun changeAddressList(addresses: MutableList<LocalAddressEntity>) {
        _addressList.value = addresses
    }

    fun changeSelectedAddress(address: LocalAddressEntity) {
        _addressList.value?.forEach {
            it.isAddressCheck = it == address
        }
    }

    fun updateSelectedAddressToServer() {
        val addressDTO = AddressListDTO(addressList.value!!.map { it.toAddressListDTO() })
        addDisposable(
            addressController.addAddress(addressDTO)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, {
                    it.printStackTrace()
                })
        )
    }
}