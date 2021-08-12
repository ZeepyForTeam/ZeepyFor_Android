package com.zeepy.zeepyforandroid.community.data.entity

import com.zeepy.zeepyforandroid.community.data.remote.response.Comment

//Todo: 서버 나오면 이것도 고치자.....
data class PostingListModel(
    val postingId: Int,
    val postingType: String,
    val title: String,
    val content: String,
    val comments: List<Comment>,
    val imageList: List<String>,
    val writerId: Int,
    val writerName: String,
    val writerImage: String,
    val productName: String,
    val sharingMethod: String,
    val targetNumberOfPeople: Int,
    val time: String,
    val isCompleted: Boolean,
    val isLiked: Boolean
)