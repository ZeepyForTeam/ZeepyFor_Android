package com.zeepy.zeepyforandroid.building

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "building_deal_table",
    foreignKeys = [
        ForeignKey(
            entity = LocalBuildingEntity::class,
            parentColumns = ["id"],
            childColumns = ["building_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ])
data class LocalBuildingDealEntity(
    @PrimaryKey(autoGenerate = false)
    var dealId: Int,
    val dealCost: Int,
    val dealDate: String,
    val dealType: String,
    val deposit: Int,
    val floor: Int,
    val monthlyRent: Int,
    @ColumnInfo(name = "building_id", index = true)
    var buildingId: Int
)
