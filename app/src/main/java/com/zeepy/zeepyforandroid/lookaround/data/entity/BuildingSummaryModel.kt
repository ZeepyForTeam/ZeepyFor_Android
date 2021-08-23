package com.zeepy.zeepyforandroid.lookaround.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuildingSummaryModel (
    val buildingName: String,
    val buildingPicture: String?,
    val personalityReview: String,
    val buildingReview: String,
    val buildingType: String,
    val roomType: String,
    val dealType: String,
    val floor: String,
    val furnitures: List<OptionModel>?,
    val pictures: List<PictureModel>?,
    val reviews: List<ReviewModel>?
): Parcelable