package com.zeepy.zeepyforandroid.localdata

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import com.zeepy.zeepyforandroid.building.LocalBuildingDealEntity
import com.zeepy.zeepyforandroid.building.LocalBuildingEntity
import com.zeepy.zeepyforandroid.building.LocalBuildingLikeEntity
import com.zeepy.zeepyforandroid.building.LocalReviewEntity
import com.zeepy.zeepyforandroid.building.dao.BuildingDao

@Database(
    entities = [LocalAddressEntity::class, LocalBuildingEntity::class, LocalBuildingDealEntity::class, LocalBuildingLikeEntity::class, LocalReviewEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ZeepyDatabase : RoomDatabase() {
    abstract fun getzeepyDao(): ZeepyDao

    abstract fun buildingDao(): BuildingDao
}