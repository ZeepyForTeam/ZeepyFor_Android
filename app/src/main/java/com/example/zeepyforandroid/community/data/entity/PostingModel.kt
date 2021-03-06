package com.example.zeepyforandroid.community.data.entity

import android.os.Parcelable
import com.example.zeepyforandroid.community.postingdetail.CommentModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostingModel(
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
):Parcelable