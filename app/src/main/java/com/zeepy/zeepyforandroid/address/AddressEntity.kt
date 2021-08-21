package com.zeepy.zeepyforandroid.address

data class AddressEntity(
    val cityDistinct: String,
    val isAddressCheck: Boolean,
    val primaryAddress: String
) {
    fun toLocalAddressEntity(): LocalAddressEntity =
        LocalAddressEntity(
            cityDistinct,
            isAddressCheck,
            primaryAddress

        )
}