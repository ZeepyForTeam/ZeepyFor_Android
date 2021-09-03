package com.zeepy.zeepyforandroid.building

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zeepy.zeepyforandroid.enum.BuildingType
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "building_table")
data class LocalBuildingEntity(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    val fullNumberAddress: String,
    val fullRoadNameAddress: String,
    val shortNumberAddress: String,
    val shortRoadNameAddress: String,
    val apartmentName: String,
    val areaCode: Int,
    val buildYear: Int,
    val exclusivePrivateArea: Double,
    val latitude: Double,
    val longitude: Double,
    val shortAddress: String,
    val buildingType: String
): Parcelable

