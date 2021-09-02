package com.zeepy.zeepyforandroid.review.data.dto

import android.os.Parcelable
import com.zeepy.zeepyforandroid.address.AddressEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val addresses: List<AddressEntity>,
    val id: Int,
    val name: String
): Parcelable