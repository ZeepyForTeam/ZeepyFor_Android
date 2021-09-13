package com.zeepy.zeepyforandroid.localdata.mapper

import com.zeepy.zeepyforandroid.building.*
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO

object LikeMapper {
    fun LocalBuildingLikeEntity.toDomain(): BuildingLikeDTO = this.let {
        BuildingLikeDTO(
            id = it.likeId,
            email = it.email,
            likeDate = it.likeDate,
            buildingId = it.buildingId
        )
    }

    fun BuildingLikeDTO.toEntity(): LocalBuildingLikeEntity = this.let {
        LocalBuildingLikeEntity(
            likeId = it.id,
            email = it.email,
            likeDate = it.likeDate,
            buildingId = it.buildingId
        )
    }
}