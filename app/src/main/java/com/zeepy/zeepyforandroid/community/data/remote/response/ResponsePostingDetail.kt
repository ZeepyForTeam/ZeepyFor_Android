package com.zeepy.zeepyforandroid.community.data.remote.response

import android.content.Context
import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import com.zeepy.zeepyforandroid.community.data.entity.UrlPictureModel
import com.zeepy.zeepyforandroid.community.data.remote.response.ResponsePostingDetail.PostingType.Companion.toPostingType
import com.zeepy.zeepyforandroid.enum.PostingType.Companion.convertToCommunityTypeString
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import com.zeepy.zeepyforandroid.util.DateParser.convertDateFormat
import com.zeepy.zeepyforandroid.util.DateParser.diffFromCreatedTime
import java.lang.IllegalArgumentException

data class ResponsePostingDetail(
    val id: Int,
    val communityCategory: String,
    val address: String,
    val productName: String,
    val sharingMethod: String,
    val targetNumberOfPeople: Int,
    val title: String,
    val content: String,
    val user: User,
    val isLiked: Boolean,
    val isParticipant: Boolean,
    val comments: List<Comment>?,
    val participants: List<Participant>,
    val imageUrls: List<String>,
    val createdTime: String,
    val isCompleted: Boolean
){
    fun toPostingDetailModel(): PostingDetailModel {
        return PostingDetailModel(
            user.id,
            user.profileImage,
            user.name,
            diffFromCreatedTime(createdTime),
            toPostingType(communityCategory),
            title,
            content,
            isCompleted,
            isLiked,
            targetNumberOfPeople,
            participants,
            imageUrls.map { UrlPictureModel(it)},
            comments?.map { it.toCommentModel() }?.toMutableList()
        )
    }

    enum class PostingType(val tag: String) {
        JOINTPURCHASE("공구"),
        FREESHARING("나눔"),
        NEIGHBORHOODFRIEND( "친구");

        companion object {
            fun toPostingType(type: String): String {
                return values().find {
                    it.name == type
                }?.tag ?: throw IllegalArgumentException("Posting type Error")
            }
        }
    }
}