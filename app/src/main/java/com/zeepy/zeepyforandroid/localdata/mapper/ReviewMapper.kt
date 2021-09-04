package com.zeepy.zeepyforandroid.localdata.mapper

import com.zeepy.zeepyforandroid.building.LocalReviewEntity
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO
import com.zeepy.zeepyforandroid.review.data.dto.User

object ReviewMapper {
    fun LocalReviewEntity.toDomain(): ResponseReviewDTO = this.let {
        ResponseReviewDTO(
            id = it.id,
            communcationTendency = it.communcationTendency,
            furnitures = it.furnitures,
            imageUrls = it.imageUrls,
            lessorAge = it.lessorAge,
            lessorGender = it.lessorGender,
            lessorReview = it.lessorReview,
            lightning = it.lightning,
            pest = it.pest,
            review = it.review,
            roomCount = it.roomCount,
            soundInsulation = it.soundInsulation,
            totalEvaluation = it.totalEvaluation,
            user = it.user,
            waterPressure = it.waterPressure
        )
    }
}