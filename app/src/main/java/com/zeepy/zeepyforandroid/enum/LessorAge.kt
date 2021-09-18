package com.zeepy.zeepyforandroid.enum

import java.lang.IllegalArgumentException

enum class LessorAge(val age: String?) {
    TEN("10 대"),
    TWENTY("20 대"),
    THIRTY("30 대"),
    FOURTY("40 대"),
    FIFTY("50 대"),
    SIXTY("60 대 이상"),
    UNKNOWN("모르겠어요");

    companion object {
        fun findLessorAge(age: String): LessorAge {
            return values().find { it.age == age } ?: throw IllegalArgumentException("Age Not Matched")
        }
    }
}