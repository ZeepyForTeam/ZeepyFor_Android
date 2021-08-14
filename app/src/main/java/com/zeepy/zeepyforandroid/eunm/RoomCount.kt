package com.zeepy.zeepyforandroid.eunm

import androidx.annotation.StringRes
import com.zeepy.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class RoomCount(@StringRes val roomCount: Int) {
    ONE(R.string.roomcount_one),
    TWO(R.string.roomcount_two),
    THREEORMORE(R.string.roomcount_threeormore);

    companion object {
        fun findRoomCount(roomCount: Int): String {
            return values().find { it.roomCount == roomCount }?.name ?: throw IllegalArgumentException("RoomCount Not Matched")
        }
    }
}