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
            postingId = id,
            postingType = communityCategory,
            title = title,
            content = content,
            imageList = imageUrls,
            comments = comments,
            writerId = user.id,
            writerName = user.name,
            writerImage = user.profileImage,
            productName = productName,
            sharingMethod = sharingMethod,
            targetNumberOfPeople = targetNumberOfPeople,
            time = createdTime,
            isCompleted = isCompleted,
            isLiked = isLiked
        )
}
