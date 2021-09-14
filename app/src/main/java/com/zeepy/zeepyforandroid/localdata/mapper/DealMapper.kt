package com.zeepy.zeepyforandroid.localdata.mapper

import com.zeepy.zeepyforandroid.building.BuildingDealDTO
import com.zeepy.zeepyforandroid.building.LocalBuildingDealEntity
import com.zeepy.zeepyforandroid.building.LocalReviewEntity
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO

object DealMapper {

    fun LocalBuildingDealEntity.toDomain(): BuildingDealDTO = this.let {
        BuildingDealDTO(
            id = it.dealId,
            dealCost = it.dealCost,
            dealDate = it.dealDate,
            dealType = it.dealType,
            deposit = it.deposit,
            floor = it.floor,
            monthlyRent = it.monthlyRent,
            buildingId = it.buildingId
        )
    }

    fun List<BuildingDealDTO>.toEntity(buildingId: Int): List<LocalBuildingDealEntity> = this.let {
        it.map { buildingDeal ->
            buildingDeal.toEntity(buildingId)
        }
    }

    private fun BuildingDealDTO.toEntity(buildingId: Int): LocalBuildingDealEntity = this.let {
        LocalBuildingDealEntity(
            dealId = it.id,
            dealCost = it.dealCost,
            dealDate = it.dealDate,
            dealType = it.dealType,
            deposit = it.deposit,
            floor = it.floor,
            monthlyRent = it.monthlyRent,
            buildingId = buildingId
        )
    }
}