package com.zeepy.zeepyforandroid.building

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuildingLikeDTO(
    val email: String,
    val id: Int,
    val likeDate: String,
    val buildingId: Int,
): Parcelable