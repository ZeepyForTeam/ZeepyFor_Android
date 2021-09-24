package com.zeepy.zeepyforandroid.address.dto

import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel

data class SearchBuildingAddressDTO(
    val apartmentName: String,
    val fullNumberAddress: String,
    val fullRoadNameAddress: String,
    val id: Int,
    val shortAddress: String,
    val shortNumberAddress: String,
    val shortRoadNameAddress: String
) {
    fun toSearchAddressListModel() =
        SearchAddressListModel(
            id,
            shortAddress,
            shortNumberAddress,
            shortRoadNameAddress
        )
}