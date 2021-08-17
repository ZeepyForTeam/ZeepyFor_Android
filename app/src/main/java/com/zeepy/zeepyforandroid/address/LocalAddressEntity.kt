package com.zeepy.zeepyforandroid.address

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "address_table", primaryKeys = ["cityDistinct", "primaryAddress"])
data class LocalAddressEntity(
    @ColumnInfo(name = "cityDistinct")
    val cityDistinct: String,
    @ColumnInfo(name = "primaryAddress")
    val primaryAddress: String
): Parcelable