package com.zeepy.zeepyforandroid.address

import com.zeepy.zeepyforandroid.address.datasource.NotAuthSearchAddressDataSource
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import io.reactivex.Single
import javax.inject.Inject

class SearchAddressListRepositoryImpl @Inject constructor(
    private val searchAddressDataSource: NotAuthSearchAddressDataSource
): SearchAddressListRepository{
    override fun searchBuildingAddressList(address: String): Single<List<SearchAddressListModel>> {
        return searchAddressDataSource.searchBuildingAddress(address).map { response ->
            response.content.map {
                it.toSearchAddressListModel()
            }
        }
    }
}