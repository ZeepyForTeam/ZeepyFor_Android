package com.zeepy.zeepyforandroid.util

object ZeepyStringBuilder {
    fun buildLessorAgeAndGenderStmt(age: String, gender: String): String {
        val genderConverted = if (gender == "MALE") "남자" else "여자"
        return age + "대 " + genderConverted + "로 보여요."
    }
}