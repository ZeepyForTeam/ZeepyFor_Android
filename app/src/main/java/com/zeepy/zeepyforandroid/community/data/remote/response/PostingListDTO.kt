package com.zeepy.zeepyforandroid.community.data.remote.response

import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel
import com.zeepy.zeepyforandroid.enum.PostingType.Companion.convertToCommunityTypeString
import com.zeepy.zeepyforandroid.util.DateParser.diffFromCreatedTime

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
            convertToCommunityTypeString(communityCategory),
            isCompleted,
            title,
            content,
            diffFromCreatedTime(createdTime)
        )
}