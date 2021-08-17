package com.zeepy.zeepyforandroid.localdata

import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import io.reactivex.Maybe
import javax.inject.Inject

class ZeepyLocalRepositoryImpl @Inject constructor(
    private val zeepyLocalDatabase: ZeepyDatabase
) : ZeepyLocalRepository {
    override fun getAddressList(): Maybe<List<LocalAddressEntity>> =
        zeepyLocalDatabase.getzeepyDao().getAddressList()

    override fun insertAllAddress(addressList: List<LocalAddressEntity>) =
        zeepyLocalDatabase.getzeepyDao().insertAllAddress(addressList)
}