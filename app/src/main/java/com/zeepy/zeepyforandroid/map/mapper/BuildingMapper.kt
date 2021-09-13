package com.zeepy.zeepyforandroid.map.mapper

import com.zeepy.zeepyforandroid.building.BuildingsAllDTO
import com.zeepy.zeepyforandroid.building.ResponseBuildingInfoDTO
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.map.data.*
import com.zeepy.zeepyforandroid.map.mapper.BuildingMapper.toDomainModel

object BuildingMapper {
    fun BuildingsAllDTO.toDomainModel(): List<BuildingModel> {
        return this.content.map {
            it.toDomainModel()
        }
    }

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
            fullNumberAddress = this.fullNumberAddress,
            fullRoadNameAddress = this.fullRoadNameAddress,
            shortRoadNameAddress = this.shortRoadNameAddress,
            buildYear = this.buildYear
        )
    }

    fun ResultSearchKeyword.toDomainModel(): List<BuildingModel> {
        return this.documents.map {
            it.toDomainModel()
        }
    }

    fun Place.toDomainModel(): BuildingModel {
        return BuildingModel(
            id = this.id.toInt(),
            apartmentName = this.place_name,
            areaCode = -1,
            fullNumberAddress = this.address_name,
            shortRoadNameAddress = this.road_address_name,
            shortAddress = this.address_name,
            latitude = this.y.toDouble(),
            longitude = this.x.toDouble(),
            fullRoadNameAddress = this.road_address_name,
            buildYear = -1,
            reviews = emptyList()
        )
    }

    fun ResultSearchAddress.toDomainModel(): List<BuildingModel> {
        return this.documents.map {
            it.toDomainModel()
        }
    }

    fun Content.toDomainModel(): BuildingModel {
        return BuildingModel(
            id = -1,
            fullRoadNameAddress = this.address_name,
            fullNumberAddress = this.address_name,
            areaCode = -1,
            shortAddress = this.address_name,
            latitude = this.y.toDouble(),
            longitude = this.x.toDouble(),
            buildYear = null,
            reviews = emptyList(),
            apartmentName = null,
            shortRoadNameAddress = this.address_name
        )
    }
}