package com.zeepy.zeepyforandroid.community.data.entity

data class PostingListModel(
    val id: Int,
    val communityType: String,
    val isCompleted: Boolean,
    val title: String,
    val content: String,
    val createdTime: String
)