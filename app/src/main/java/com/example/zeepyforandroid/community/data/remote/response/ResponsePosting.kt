package com.example.zeepyforandroid.community.data.remote.response

import com.example.zeepyforandroid.community.data.entity.UrlPictureModel
import com.example.zeepyforandroid.community.data.remote.response.ResponsePosting.PostingType.Companion.toPostingType
import com.example.zeepyforandroid.community.data.entity.CommentModel
import com.example.zeepyforandroid.community.data.entity.PostingDetailModel
import java.lang.IllegalArgumentException

//Fixme: 서버 api나오면 변경하기
data class ResponsePosting(
    val writerUserIdx: Int,
    val imageWriter: String,
    val nameWriter: String,
    val postingTime: String,
    val typePosting: String,
    val titlePosting: String,
    val contentPosting: String,
    val postingStatus: Boolean,
    val picturesPosting: List<UrlPictureModel>,
    val isSetAchievement: Boolean,
    val achievementRate: Int,
    val comments: List<CommentModel>?
) {
    fun toPostingModel(): PostingDetailModel =
        PostingDetailModel(
            writerUserIdx,
            imageWriter,
            nameWriter,
            postingTime,
            toPostingType(typePosting),
            titlePosting,
            contentPosting,
            postingStatus,
            picturesPosting,
            isSetAchievement,
            comments?.toMutableList()
        )

    enum class PostingType(val tag: String) {
        JOINTPURCHASE("공구"),
        FREESHARING("나눔"),
        NEIGHBORHOODFRIEND( "친구");

        companion object {
            fun toPostingType(type: String): String {
                return values().find {
                    it.name== type
                }?.tag ?: throw IllegalArgumentException("Posting type Error")
            }
        }
    }
}