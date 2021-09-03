package com.zeepy.zeepyforandroid.building.dao

import androidx.room.*
import com.zeepy.zeepyforandroid.building.BuildingPropertiesRelations
import com.zeepy.zeepyforandroid.building.LocalBuildingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BuildingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuilding(building: LocalBuildingEntity)

    @Transaction
    @Query("SELECT * FROM building_table WHERE id = :id")
    fun getBuildingById(id: Int): Flow<BuildingPropertiesRelations>

    @Query("DELETE FROM building_table WHERE id = :id")
    suspend fun deleteBuildingById(id: Int)
}