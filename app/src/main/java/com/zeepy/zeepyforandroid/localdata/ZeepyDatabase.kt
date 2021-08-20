package com.zeepy.zeepyforandroid.localdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zeepy.zeepyforandroid.address.LocalAddressEntity

@Database(entities = [LocalAddressEntity::class], version = 1, exportSchema = false)
abstract class ZeepyDatabase : RoomDatabase() {
    abstract fun getzeepyDao(): ZeepyDao
}