package com.zeepy.zeepyforandroid.enum

import androidx.annotation.StringRes
import com.zeepy.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class DealType(@StringRes val dealType: Int) {
    MONTHLY(R.string.monthly_pay),
    JEONSE(R.string.jeonse_pay);

    companion object {
        fun findDealType(dealType: Int): String {
            return values().find { it.dealType == dealType }?.name ?: throw IllegalArgumentException("RoomCount Not Matched")
        }

        fun findDealTypeFromString(text: String): Int {
            return DealType.values().find {
                it.name == text
            }?.dealType ?: throw IllegalArgumentException("String and Name Not Matched")
        }
    }
}