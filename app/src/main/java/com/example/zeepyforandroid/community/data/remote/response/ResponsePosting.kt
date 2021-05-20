package com.example.zeepyforandroid.community.data.remote.response

import com.example.zeepyforandroid.community.data.entity.PostingModel
import com.example.zeepyforandroid.community.data.entity.UrlPictureModel
import com.example.zeepyforandroid.community.data.remote.response.ResponsePosting.PostingType.Companion.toPostingType
import java.lang.IllegalArgumentException

//Fixme: 서버 api나오면 변경하기
data class ResponsePosting(
    val imageWriter: String,
    val nameWriter: String,
    val postingTime: String,
    val typePosting: Int,
    val titlePosting: String,
    val contentPosting: String,
    val picturesPosting: List<UrlPictureModel>,
    val isSetAchievement: Boolean,
    val achivementRate: Int
) {
    fun toPostingModel(): PostingModel =
        PostingModel(
            imageWriter,
            nameWriter,
            postingTime,
            toPostingType(typePosting).tag,
            titlePosting,
            contentPosting,
            picturesPosting,
            isSetAchievement
        )

    enum class PostingType(val typePosting: Int,val  tag: String) {
        GROUP_PURCHASE(1, "공구"),
        SHARING(2, "무료나눔"),
        FRIENDS(3, "동네친구");

        companion object {
            fun toPostingType(type: Int): PostingType {
                return values().find {
                    it.typePosting == type
                } ?: throw IllegalArgumentException("Posting type Error")
            }
        }
    }
}