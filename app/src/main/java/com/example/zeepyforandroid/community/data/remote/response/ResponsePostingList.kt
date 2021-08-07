package com.example.zeepyforandroid.community.data.remote.response

import com.example.zeepyforandroid.community.data.entity.PostingListModel

data class ResponsePostingList(
    val address: String,
    val comments: List<Comment>,
    val communityCategory: String,
    val content: String,
    val createdTime: String,
    val id: Int,
    val imageUrls: List<String>,
    val isCompleted: Boolean,
    val isLiked: Boolean,
    val isParticipant: Boolean,
    val participants: List<Participant>,
    val productName: String,
    val sharingMethod: String,
    val targetNumberOfPeople: Int,
    val title: String,
    val user: User
) {
    fun toPostingListModel(): PostingListModel =
        PostingListModel(
            communityCategory,
            isCompleted,
            title,
            content,
            createdTime
        )
}