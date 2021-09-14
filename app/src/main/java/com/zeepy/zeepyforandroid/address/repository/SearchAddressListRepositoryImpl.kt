package com.zeepy.zeepyforandroid.address.repository

import com.zeepy.zeepyforandroid.address.datasource.SearchAddressDataSource
import com.zeepy.zeepyforandroid.lookaround.data.entity.SearchAddressForLookAroundModel
import com.zeepy.zeepyforandroid.lookaround.mapper.AddressMapper.toDomainModel
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import io.reactivex.Single
import javax.inject.Inject

class SearchAddressListRepositoryImpl @Inject constructor(
    private val searchAddressDataSource: SearchAddressDataSource
): SearchAddressListRepository {
    override fun searchBuildingAddressList(address: String): Single<List<SearchAddressListModel>> {
        return searchAddressDataSource.searchBuildingAddress(address).map { response ->
            response.content.map {
                it.toSearchAddressListModel()
            }
        }
    }

    override suspend fun searchBuildingsByAddress(address: String, page: Int): SearchAddressForLookAroundModel? {
        return searchAddressDataSource.searchBuildingsByAddress(address, page)?.toDomainModel()
    }

}