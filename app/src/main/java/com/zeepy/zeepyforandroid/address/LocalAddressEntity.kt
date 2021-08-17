package com.zeepy.zeepyforandroid.address

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address_table", primaryKeys = ["cityDistinct", "primaryAddress"])
data class LocalAddressEntity(
    @ColumnInfo(name = "cityDistinct")
    val cityDistinct: String,
    @ColumnInfo(name = "primaryAddress")
    val primaryAddress: String
)