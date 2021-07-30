package com.example.zeepyforandroid.eunm

import androidx.annotation.StringRes
import com.example.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class LessorGender(@StringRes val gender: Int) {
    MALE(R.string.male),
    FEMALE(R.string.female);

    companion object {
        fun findGender(gender: Int): String {
            return values().find { it.gender == gender }?.name ?: throw IllegalArgumentException("Gender Not Matched")
        }
    }
}