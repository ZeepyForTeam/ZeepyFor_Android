package com.zeepy.zeepyforandroid.community.data.remote.responseDTO

import com.zeepy.zeepyforandroid.community.data.entity.CommentModel
import com.zeepy.zeepyforandroid.community.data.entity.NestedCommentModel

data class Comment(
    val comment: String,
    val communityId: Int,
    val createdTime: String,
    val id: Int,
    val isParticipation: Boolean,
    val isSecret: Boolean,
    val subComments: List<Comment?>?,
    val superCommentId: Int?,
    val writer: Writer
) {
    fun toCommentModel(): CommentModel {
        return CommentModel(
            writer.id,
            writer.profileImage,
            writer.name,
            comment,
            createdTime,
            isSecret,
            toNestedCommentModel()
        )
    }

    private fun toNestedCommentModel(): List<NestedCommentModel>? {
        return subComments?.map { comment ->
            NestedCommentModel(
                comment?.writer?.id,
                comment?.writer?.name,
                comment?.comment,
                comment?.isSecret,
                comment?.createdTime
            )
        }
    }
}