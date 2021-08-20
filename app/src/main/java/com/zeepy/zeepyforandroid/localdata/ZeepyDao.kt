package com.zeepy.zeepyforandroid.localdata

import androidx.room.*
import com.zeepy.zeepyforandroid.address.LocalAddressEntity
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ZeepyDao {
    @Query("SELECT * FROM address_table")
    fun fetchAddressList(): Maybe<List<LocalAddressEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAddress(addressList: List<LocalAddressEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddress(address: LocalAddressEntity)

    @Delete
    fun deleteAddress(address: LocalAddressEntity)
}