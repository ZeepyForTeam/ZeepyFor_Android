package com.zeepy.zeepyforandroid.community.data.remote.requestDTO

data class RequestWriteCommentDTO(
    val comment: String,
    val isSecret: Boolean,
    val superCommentId: Int?
)