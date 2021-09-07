package com.zeepy.zeepyforandroid.localdata

import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import io.reactivex.rxjava3.core.Maybe
import javax.inject.Inject

class ZeepyLocalRepositoryImpl @Inject constructor(
    private val zeepyLocalDatabase: ZeepyDatabase
) : ZeepyLocalRepository {
    val zeepyDao = zeepyLocalDatabase.getzeepyDao()
    override fun fetchAddressList(): Maybe<List<LocalAddressEntity>> = zeepyDao.fetchAddressList()
    override fun insertAllAddress(addressList: List<LocalAddressEntity>) = zeepyDao.insertAllAddress(addressList)
    override fun insertAddress(address: LocalAddressEntity) = zeepyDao.insertAddress(address)
    override fun deleteAddress(address: LocalAddressEntity) = zeepyDao.deleteAddress(address)
    override fun deleteEveryAddress() = zeepyDao.deleteEveryAddress()
}