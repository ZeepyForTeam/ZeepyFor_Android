package com.zeepy.zeepyforandroid.localdata

import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import io.reactivex.Maybe

interface ZeepyLocalRepository {
    fun getAddressList(): Maybe<List<LocalAddressEntity>>
    fun insertAllAddress(addressList: List<LocalAddressEntity>)
    fun insertAddress(address: LocalAddressEntity)
    fun deleteAddress(address: LocalAddressEntity)
}