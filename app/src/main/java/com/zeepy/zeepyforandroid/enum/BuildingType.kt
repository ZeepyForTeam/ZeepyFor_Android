package com.zeepy.zeepyforandroid.enum

import androidx.annotation.StringRes
import com.zeepy.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class BuildingType(@StringRes val buildingType: Int) {
    OFFICETEL(R.string.officetel),
    ROWHOUSE(R.string.rowhouse);

    companion object {
        fun findBuildintType(buildingType: Int): String {
            return values().find { it.buildingType == buildingType }?.name ?: throw IllegalArgumentException("RoomCount Not Matched")
        }

        fun findBuildingTypeFromString(text: String): Int {
            return BuildingType.values().find {
                it.name == text
            }?.buildingType ?: throw IllegalArgumentException("String and Name Not Matched")
        }
    }
}