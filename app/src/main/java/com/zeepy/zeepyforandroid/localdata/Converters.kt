package com.zeepy.zeepyforandroid.localdata

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zeepy.zeepyforandroid.building.BuildingDealDTO
import com.zeepy.zeepyforandroid.review.data.dto.User

class Converters {
    @TypeConverter
    fun listToJsonString(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String?) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun userToString(user: User?): String? = Gson().toJson(user)

    @TypeConverter
    fun stringToUser(string: String?): User = Gson().fromJson(string, User::class.java)

    @TypeConverter
    fun buildingDealToJson(value: List<BuildingDealDTO>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToBuildingDeal(value: String?) = Gson().fromJson(value, Array<BuildingDealDTO>::class.java).toList()
}