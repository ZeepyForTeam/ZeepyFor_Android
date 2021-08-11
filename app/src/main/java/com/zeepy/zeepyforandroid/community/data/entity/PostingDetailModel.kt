package com.zeepy.zeepyforandroid.community.data.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostingDetailModel(
    val writerUserIdx: Int,
    val imageWriter: String,
    val nameWriter: String,
    val postingTime: String,
    val typePosting: String,
    val titlePosting: String,
    val contentPosting: String,
    val postingStatus: Boolean,
    val picturesPosting: List<UrlPictureModel>,
    val isSetAchievement: Boolean,
    val comments: MutableList<CommentModel>?
): Parcelable