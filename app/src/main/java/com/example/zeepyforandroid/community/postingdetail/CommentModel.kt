package com.example.zeepyforandroid.community.postingdetail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentModel(
    val profile: String?,
    val nickname: String,
    val comment: String,
    val date: String,
    val nestedComments: List<NestedCommentModel>?
) : Parcelable