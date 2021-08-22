package com.zeepy.zeepyforandroid.community.data.remote.response

import com.zeepy.zeepyforandroid.community.data.entity.PostingListModel

data class PostingDetailDTO(
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

}
