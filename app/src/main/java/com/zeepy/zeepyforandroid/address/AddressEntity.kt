package com.zeepy.zeepyforandroid.address

import com.google.gson.annotations.SerializedName

data class AddressEntity(
    val cityDistinct: String,
    val detailAddress: String,
    val primaryAddress: String
)