package com.zeepy.zeepyforandroid.localdata.mapper

import com.zeepy.zeepyforandroid.building.BuildingPropertiesRelations
import com.zeepy.zeepyforandroid.building.LocalBuildingEntity
import com.zeepy.zeepyforandroid.localdata.mapper.DealMapper.toDomain
import com.zeepy.zeepyforandroid.localdata.mapper.LikeMapper.toDomain
import com.zeepy.zeepyforandroid.localdata.mapper.ReviewMapper.toDomain
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel

object BuildingMapper {
    fun LocalBuildingEntity.toDomain(): BuildingSummaryModel = this.let {
        BuildingSummaryModel(
            id = it.id,
            buildingName = it.apartmentName,
            buildingType = it.buildingType,
            areaCode = it.areaCode,
            exclusivePrivateArea = it.exclusivePrivateArea,
            latitude = it.latitude,
            longitude = it.longitude,
            buildYear = it.buildYear,
            shortAddress = it.shortAddress,
            shortRoadNameAddress = it.shortRoadNameAddress,
            shortNumberAddress = it.shortNumberAddress,
            fullNumberAddress = it.fullNumberAddress,
            fullRoadNameAddress = it.fullRoadNameAddress
        )
    }

    fun BuildingPropertiesRelations.toDomain(): BuildingSummaryModel = this.let {
        BuildingSummaryModel(
            id = it.building.id,
            buildingName = it.building.apartmentName,
            buildingType = it.building.buildingType,
            areaCode = it.building.areaCode,
            exclusivePrivateArea = it.building.exclusivePrivateArea,
            latitude = it.building.latitude,
            longitude = it.building.longitude,
            buildYear = it.building.buildYear,
            shortAddress = it.building.shortAddress,
            shortRoadNameAddress = it.building.shortRoadNameAddress,
            shortNumberAddress = it.building.shortNumberAddress,
            fullNumberAddress = it.building.fullNumberAddress,
            fullRoadNameAddress = it.building.fullRoadNameAddress,
            buildingDeals = it.buildingDeals.map { deal -> deal.toDomain() },
            buildingLikes = it.buildingLikes.map { like -> like.toDomain() },
            reviews = it.reviews.map { review -> review.toDomain() }
        )
    }

    fun BuildingSummaryModel.toEntity(): LocalBuildingEntity = this.let {
        LocalBuildingEntity(
            id = it.id,
            apartmentName = it.buildingName,
            buildingType = it.buildingType,
            areaCode = it.areaCode,
            exclusivePrivateArea = it.exclusivePrivateArea,
            latitude = it.latitude,
            longitude = it.longitude,
            buildYear = it.buildYear,
            shortAddress = it.shortAddress,
            shortRoadNameAddress = it.shortRoadNameAddress,
            shortNumberAddress = it.shortNumberAddress,
            fullNumberAddress = it.fullNumberAddress,
            fullRoadNameAddress = it.fullRoadNameAddress
        )
    }
}