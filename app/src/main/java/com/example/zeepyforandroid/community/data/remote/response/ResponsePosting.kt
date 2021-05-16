package com.example.zeepyforandroid.community.data.remote.response

import com.example.zeepyforandroid.community.data.entity.PostingModel
import com.example.zeepyforandroid.community.data.entity.UrlPictureModel

//Fixme: 서버 api나오면 변경하기
data class ResponsePosting(
    val imageWriter: String,
    val nameWriter: String,
    val postingTime: String,
    val typePosting: String,
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
            typePosting,
            titlePosting,
            contentPosting,
            picturesPosting,
            isSetAchievement
        )
}