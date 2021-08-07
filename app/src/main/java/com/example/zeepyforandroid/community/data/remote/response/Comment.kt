package com.example.zeepyforandroid.community.data.remote.response

data class Comment(
    val comment: String,
    val communityId: Int,
    val createdTime: String,
    val id: Int,
    val isParticipation: Boolean,
    val isSecret: Boolean,
    val subComments: List<Comment>?,
    val superCommentId: Int,
    val writer: Writer
)