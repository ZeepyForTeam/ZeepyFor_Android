package com.zeepy.zeepyforandroid.address.repository

import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import io.reactivex.Single

interface SearchAddressListRepository {
    fun searchBuildingAddressList(address: String): Single<List<SearchAddressListModel>>
}