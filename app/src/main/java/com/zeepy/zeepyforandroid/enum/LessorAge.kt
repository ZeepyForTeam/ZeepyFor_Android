package com.zeepy.zeepyforandroid.enum

import java.lang.IllegalArgumentException

enum class LessorAge(val age: String) {
    TEN("10"),
    TWENTY("20"),
    THIRTY("30"),
    FOURTY("40"),
    FIFTY("50"),
    SIXTY("60"),
    SEVENTY("70"),
    EIGHTY("80");

    companion object {
        fun findLessorAge(age: String): String {
            return values().find { it.age == age }?.name ?: throw IllegalArgumentException("Age Not Matched")
        }
    }
}