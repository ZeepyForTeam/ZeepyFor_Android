package com.zeepy.zeepyforandroid.building

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.zeepy.zeepyforandroid.review.data.dto.User

@Entity(
    tableName = "review_table",
    foreignKeys = [
        ForeignKey(
            entity = LocalBuildingEntity::class,
            parentColumns = ["id"],
            childColumns = ["building_id"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ]
)
data class LocalReviewEntity(
    @PrimaryKey(autoGenerate = false)
    var reviewId: Int,
    val communcationTendency: String,
    val furnitures: List<String>,
    val imageUrls: List<String>,
    val lessorAge: String,
    val lessorGender: String,
    val lessorReview: String,
    val lightning: String,
    val pest: String,
    val review: String,
    val roomCount: String,
    val soundInsulation: String,
    val totalEvaluation: String,
    val user: User,
    val waterPressure: String,
    @ColumnInfo(name = "building_id", index = true)
    var buildingId: Int
)
