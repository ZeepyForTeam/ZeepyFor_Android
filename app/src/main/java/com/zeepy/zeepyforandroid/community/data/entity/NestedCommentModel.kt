package com.zeepy.zeepyforandroid.community.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NestedCommentModel(
    val writerUserIdx: Int,
    val writerName: String,
    val comment: String,
    val isSecretComment: Boolean,
    val date: String
):Parcelable