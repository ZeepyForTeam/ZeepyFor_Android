package com.zeepy.zeepyforandroid.address

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.zeepy.zeepyforandroid.address.dto.AddressListDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "address_table", primaryKeys = ["cityDistinct", "primaryAddress"])
data class LocalAddressEntity(
    @ColumnInfo(name = "cityDistinct")
    val cityDistinct: String,
    val isAddressCheck: Boolean,
    @ColumnInfo(name = "primaryAddress")
    val primaryAddress: String
): Parcelable {
    fun toAddressListDTO(): AddressEntity =
        AddressEntity(
            cityDistinct,
            isAddressCheck,
            primaryAddress

        )
}