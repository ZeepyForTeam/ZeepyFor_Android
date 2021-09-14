package com.zeepy.zeepyforandroid.lookaround.data.entity

data class SearchAddressForLookAroundModel(
    val addresses: List<AddressDetailsModel>,
    val totalPages: Int,
    val last: Boolean,
    val number: Int
)
