package com.zeepy.zeepyforandroid.community.data.entity

import android.os.Parcelable
import com.zeepy.zeepyforandroid.community.data.remote.responseDTO.Participant
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostingDetailModel(
    val writerUserIdx: Int,
    val imageWriter: String,
    val nameWriter: String,
    val postingTime: String,
    val typePosting: String,
    val titlePosting: String,
    val contentPosting: String,
    val postingStatus: Boolean,
    val isLiked: Boolean,
    val instructions: String,
    val productName: String?,
    val isParticipant: Boolean,
    val purchasePlace: String?,
    val productPrice: String?,
    val sharingMethod: String?,
    val targetNumberOfPeople: Int?,
    val participants: List<Participant>,
    val picturesPosting: List<UrlPictureModel>,
    val comments: MutableList<CommentModel?>?
): Parcelable