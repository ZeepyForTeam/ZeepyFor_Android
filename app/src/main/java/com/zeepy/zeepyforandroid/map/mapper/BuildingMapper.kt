package com.zeepy.zeepyforandroid.map.mapper

import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.map.data.BuildingModel

object BuildingMapper {
    fun List<ResponseBuildingInfoDTO>.toDomainModel(): List<BuildingModel> {
        return this.map {
            it.toDomainModel()
        }
    }

    // FIXME: Change according to BuildingModel
    private fun ResponseBuildingInfoDTO.toDomainModel(): BuildingModel {
        return BuildingModel(
            id = this.id,
            apartmentName = this.apartmentName,
            areaCode = this.areaCode,
            shortAddress = this.shortAddress,
            latitude = this.latitude,
            longitude = this.longitude,
            reviews = this.reviews,
            fullRoadNameAddress = this.fullRoadNameAddress,
            shortRoadNameAddress = this.shortRoadNameAddress,
            buildYear = this.buildYear
        )
    }
}