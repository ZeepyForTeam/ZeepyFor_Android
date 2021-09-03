package com.zeepy.zeepyforandroid.building

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuildingDealDTO(
    val dealCost: Int,
    val dealDate: String,
    val dealType: String,
    val deposit: Int,
    val floor: Int,
    val id: Int,
    val monthlyRent: Int,
    val buildingId: Int,
): Parcelable