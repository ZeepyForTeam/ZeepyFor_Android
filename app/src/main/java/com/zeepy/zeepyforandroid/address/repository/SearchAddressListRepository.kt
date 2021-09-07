package com.zeepy.zeepyforandroid.address.repository

import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import io.reactivex.rxjava3.core.Single

interface SearchAddressListRepository {
    fun searchBuildingAddressList(address: String): Single<List<SearchAddressListModel>>
}