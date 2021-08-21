package com.zeepy.zeepyforandroid.review.data.dto

import com.zeepy.zeepyforandroid.address.AddressEntity

data class User(
    val addresses: List<AddressEntity>,
    val id: Int,
    val name: String
)