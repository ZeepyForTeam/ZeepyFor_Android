package com.zeepy.zeepyforandroid.building.dao

import androidx.room.*
import com.zeepy.zeepyforandroid.building.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface BuildingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuilding(building: LocalBuildingEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuildingDeals(buildingDeal: LocalBuildingDealEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuildingLikes(buildingLike: LocalBuildingLikeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuildingReviews(buildingReview: LocalReviewEntity)

    @Transaction
    @Query("SELECT * FROM building_table WHERE id = :id")
    fun getBuildingById(id: Int): Flow<BuildingPropertiesRelations>

    @Query("DELETE FROM building_table WHERE id = :id")
    suspend fun deleteBuildingById(id: Int)

    @Query("SELECT EXISTS(SELECT * FROM building_table WHERE id = :id)")
    suspend fun isRowExists(id: Int): Boolean
}