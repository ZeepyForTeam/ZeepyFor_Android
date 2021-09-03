package com.zeepy.zeepyforandroid.lookaround.mapper

import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.OptionModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.PictureModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.ReviewModel
import com.zeepy.zeepyforandroid.map.data.BuildingModel

object BuildingMapper {
    fun ResponseBuildingInfoDTO.toDomainModel(): BuildingSummaryModel {
        return BuildingSummaryModel(
            id = this.id,
            buildingName = this.apartmentName,
            buildingType = this.buildingType,
            fullNumberAddress = this.fullNumberAddress,
            fullRoadNameAddress = this.fullRoadNameAddress,
            shortNumberAddress = this.shortNumberAddress,
            shortAddress = this.shortAddress,
            shortRoadNameAddress = this.shortRoadNameAddress,
            areaCode = this.areaCode,
            buildYear = this.buildYear,
            exclusivePrivateArea = this.exclusivePrivateArea,
            latitude = this.latitude,
            longitude = this.longitude,
            buildingDeals = this.buildingDeals,
            buildingLikes = this.buildingLikes,
            reviews = this.reviews
        )
    }
}