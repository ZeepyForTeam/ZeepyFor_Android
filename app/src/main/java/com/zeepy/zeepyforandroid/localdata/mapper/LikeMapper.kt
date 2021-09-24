package com.zeepy.zeepyforandroid.localdata.mapper

import com.zeepy.zeepyforandroid.building.*
import com.zeepy.zeepyforandroid.localdata.mapper.DealMapper.toEntity
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO

object LikeMapper {
    fun LocalBuildingLikeEntity.toDomain(): BuildingLikeDTO = this.let {
        BuildingLikeDTO(
            id = it.likeId,
            userId = it.userId,
            likeDate = it.likeDate,
            buildingId = it.buildingId
        )
    }

    fun List<BuildingLikeDTO>.toEntity(buildingId: Int): List<LocalBuildingLikeEntity> = this.let {
        it.map { buildingLike ->
            buildingLike.toEntity(buildingId)
        }
    }

    private fun BuildingLikeDTO.toEntity(buildingId: Int): LocalBuildingLikeEntity = this.let {
        LocalBuildingLikeEntity(
            likeId = it.id,
            userId = it.userId,
            likeDate = it.likeDate,
            buildingId = buildingId
        )
    }
}