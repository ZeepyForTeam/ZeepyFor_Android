package com.example.zeepyforandroid.community.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommentModel(
    val commentWriterIdx: Int,
    val profile: String?,
    val nickname: String,
    val comment: String,
    val date: String,
    val isSecretComment: Boolean,
    val nestedComments: List<NestedCommentModel>?
) : Parcelable