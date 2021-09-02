package com.zeepy.zeepyforandroid.lookaround.mapper

import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.OptionModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.PictureModel
import com.zeepy.zeepyforandroid.lookaround.data.entity.ReviewModel
import com.zeepy.zeepyforandroid.map.data.BuildingModel

object BuildingMapper {
    // FIXME: Change according to BuildingModel
    fun ResponseBuildingInfoDTO.toDomainModel(): BuildingSummaryModel {
        return BuildingSummaryModel(
            buildingName = this.apartmentName,
            buildingType = this.buildingType,
            buildingDeals = this.buildingDeals,
            reviews = this.reviews
        )
    }
}