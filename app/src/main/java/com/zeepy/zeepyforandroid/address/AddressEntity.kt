package com.zeepy.zeepyforandroid.address

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddressEntity(
    val cityDistinct: String,
    val isAddressCheck: Boolean,
    val primaryAddress: String
): Parcelable {
    fun toLocalAddressEntity(): LocalAddressEntity =
        LocalAddressEntity(
            cityDistinct,
            isAddressCheck,
            primaryAddress

        )
}