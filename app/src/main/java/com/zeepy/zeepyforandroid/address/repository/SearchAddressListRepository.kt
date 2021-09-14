package com.zeepy.zeepyforandroid.address.repository

import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.SearchAddressForLookAroundModel
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import io.reactivex.Single

interface SearchAddressListRepository {
    fun searchBuildingAddressList(address: String): Single<List<SearchAddressListModel>>
    //coroutine
    suspend fun searchBuildingsByAddress(address: String, page: Int): SearchAddressForLookAroundModel?
}