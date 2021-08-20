package com.zeepy.zeepyforandroid.localdata

import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import io.reactivex.Maybe
import javax.inject.Inject

class ZeepyLocalRepositoryImpl @Inject constructor(
    private val zeepyLocalDatabase: ZeepyDatabase
) : ZeepyLocalRepository {
    override fun fetchAddressList(): Maybe<List<LocalAddressEntity>> =
        zeepyLocalDatabase.getzeepyDao().fetchAddressList()

    override fun insertAllAddress(addressList: List<LocalAddressEntity>) =
        zeepyLocalDatabase.getzeepyDao().insertAllAddress(addressList)

    override fun insertAddress(address: LocalAddressEntity) =
        zeepyLocalDatabase.getzeepyDao().insertAddress(address)

    override fun deleteAddress(address: LocalAddressEntity) =
        zeepyLocalDatabase.getzeepyDao().deleteAddress(address)
}