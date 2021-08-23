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
        val current = localDateFormat.parse(getCurrentDate())
        val created = localDateFormat.parse(convertDateFormat(date))
        var hours = 0.0.toLong()
        var minutes = 0.0.toLong()
        var seconds = 0.0.toLong()
        var days = 0.0.toLong()

        try {
            val diff:Long = current.time - created.time
            seconds = diff/1000
            minutes = seconds/60
            hours = minutes/60
            days = hours/24
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return if (days >= 1) {
            "${days}일 전"
        } else if(hours >= 1) {
            "${hours}시간 "
        } else if (minutes >= 1) {
            "${minutes}분 전"
        } else {
            "${seconds}초 전"
        }
    }
}