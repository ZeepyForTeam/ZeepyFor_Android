package com.example.zeepyforandroid.eunm

import androidx.annotation.StringRes
import com.example.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class RoomCount(@StringRes val roomCount: Int) {
    ONE(R.string.roomcount_one),
    TWO(R.string.roomcount_two),
    THREEORMORE(R.string.roomcount_threeormore);

    fun findRoomCount(roomCount: Int): String {
        return values().find { it.roomCount == roomCount }?.name ?: throw IllegalArgumentException("RoomCount Not Matched")
    }
}