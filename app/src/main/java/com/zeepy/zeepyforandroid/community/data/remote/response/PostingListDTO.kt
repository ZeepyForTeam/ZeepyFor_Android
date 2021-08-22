package com.zeepy.zeepyforandroid.community.data.remote.response

import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel

data class PostingListDTO(
    val communityCategory: String,
    val content: String,
    val createdTime: String,
    val id: Int,
    val isCompleted: Boolean,
    val title: String
) {
    fun toPostingListModel(): PostingListModel =
        PostingListModel(
            id,
            communityCategory,
            isCompleted,
            title,
            content,
            createdTime
        )
}