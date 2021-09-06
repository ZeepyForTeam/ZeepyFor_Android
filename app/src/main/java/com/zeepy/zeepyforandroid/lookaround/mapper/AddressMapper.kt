package com.zeepy.zeepyforandroid.lookaround.mapper

import com.zeepy.zeepyforandroid.address.dto.ResponseSearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.address.dto.SearchBuildingAddressDTO
import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.lookaround.data.entity.AddressDetailsModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.SearchAddressForLookAroundModel
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel

object AddressMapper {
    fun ResponseSearchBuildingAddressDTO.toDomainModel(): SearchAddressForLookAroundModel {
        return SearchAddressForLookAroundModel(
            this.content.map {
                it.toDomainModel()
            },
            totalPages,
            number
        )
    }

    private fun SearchBuildingAddressDTO.toDomainModel(): AddressDetailsModel {
        return AddressDetailsModel(
            id = this.id,
            cityDistinct = this.shortAddress,
            primaryAddress = this.shortNumberAddress,
            shortRoadNameAddress = this.shortRoadNameAddress,
            apartmentName = this.apartmentName,
            fullNumberAddress = this.fullNumberAddress,
            fullRoadNameAddress = this.fullRoadNameAddress
        )
    }
}