package com.zeepy.zeepyforandroid.lookaround.data.entity

import android.os.Parcelable
import com.zeepy.zeepyforandroid.building.BuildingDealDTO
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuildingSummaryModel (
    val buildingName: String,
    val buildingType: String,
    val buildingDeals: List<BuildingDealDTO>,
    val reviews: List<ResponseReviewDTO>?
): Parcelable