package com.zeepy.zeepyforandroid.lookaround.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewModel(
    val writer: String,
    val roomNumber: String,
    val postedTime: String,
    val lessorReview: String,
    val houseReview: String,
    val postedPictures: List<PictureModel>
): Parcelable
