package com.example.zeepyforandroid.community.postingdetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NestedCommentModel(
    val writerName: String,
    val comment: String,
    val date: String
):Parcelable