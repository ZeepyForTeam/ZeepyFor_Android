package com.zeepy.zeepyforandroid.community.data.entity

data class CommentAuthenticatedModel(
    val currentUserIdx: Int,
    val postingWriterIdx: Int?,
    val commentWriterIdx: Int?
)