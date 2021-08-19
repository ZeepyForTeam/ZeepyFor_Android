package com.zeepy.zeepyforandroid.address

data class AddressEntity(
    val cityDistinct: String,
    val detailAddress: String,
    val primaryAddress: String
) {
    fun toLocalAddressEntity(): LocalAddressEntity =
        LocalAddressEntity(
            cityDistinct,
            primaryAddress
        )
}