package com.zeepy.zeepyforandroid.lookaround.data.entity

import android.os.Parcelable
import com.zeepy.zeepyforandroid.building.BuildingDealDTO
import com.zeepy.zeepyforandroid.building.BuildingLikeDTO
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BuildingSummaryModel (
    var id: Int,
    val buildingName: String,
    val buildingType: String,
    val fullNumberAddress: String,
    val fullRoadNameAddress: String,
    val shortNumberAddress: String,
    val shortRoadNameAddress: String,
    val areaCode: Int,
    val buildYear: Int,
    val exclusivePrivateArea: Double,
    val latitude: Double,
    val longitude: Double,
    val shortAddress: String,
    val buildingDeals: List<BuildingDealDTO>,
    val buildingLikes: List<BuildingLikeDTO>,
    val reviews: List<ResponseReviewDTO>
): Parcelable {
    // FIXME: 두 모델 중 하나로 통일시키기
    fun toBuildingModel() =
        BuildingModel(
            id,
            buildYear,
            buildingName,
            shortAddress,
            fullNumberAddress,
            fullRoadNameAddress,
            shortRoadNameAddress,
            areaCode,
            latitude,
            longitude,
            reviews
        )
}