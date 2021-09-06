package com.zeepy.zeepyforandroid.address.dto

data class ResponseSearchBuildingAddressDTO(
    val content: List<SearchBuildingAddressDTO>,
    val totalPages: Int,
    val number: Int
)