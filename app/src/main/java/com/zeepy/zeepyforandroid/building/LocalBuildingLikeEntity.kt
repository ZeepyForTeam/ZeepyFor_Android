package com.zeepy.zeepyforandroid.building

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "building_like_table",
    foreignKeys = [
        ForeignKey(
            entity = LocalBuildingEntity::class,
            parentColumns = ["id"],
            childColumns = ["building_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ])
data class LocalBuildingLikeEntity(
    @PrimaryKey(autoGenerate = false)
    var likeId: Int,
    val userId: String,
    val likeDate: String,
    @ColumnInfo(name = "building_id", index = true)
    var buildingId: Int
)
