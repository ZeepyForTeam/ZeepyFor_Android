package com.zeepy.zeepyforandroid.building

import androidx.room.Embedded
import androidx.room.Relation

data class BuildingPropertiesRelations(
    @Embedded val building: LocalBuildingEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "building_id"
    )
    val reviews: List<LocalReviewEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "building_id"
    )
    val buildingDeals: List<LocalBuildingDealEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "building_id"
    )
    val buildingLikes: List<LocalBuildingLikeEntity>
)
