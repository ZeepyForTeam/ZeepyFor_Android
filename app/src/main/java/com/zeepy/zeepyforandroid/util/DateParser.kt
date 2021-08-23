package com.zeepy.zeepyforandroid.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateParser {
    private val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS")
    private val localDateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초")

    fun getCurrentDate(): String {
        val date = Date()
        return localDateFormat.format(date)
    }

    fun convertDateFormat(date: String): String {
        var outputDate = ""

        try {
            val inputDate = serverDateFormat.parse(date)
            outputDate = localDateFormat.format(inputDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDate
    }

    fun diffFromCreatedTime(date: String): String {
        val current = convertDateFormat(date)
        return current
    }
}