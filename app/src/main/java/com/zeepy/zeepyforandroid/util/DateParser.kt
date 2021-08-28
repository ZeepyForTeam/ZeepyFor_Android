package com.zeepy.zeepyforandroid.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateParser {
    private val serverDateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS")
    private val localDateFormat = SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초")
    private val commentDateFormat = SimpleDateFormat("yyyy.MM.dd")

    fun getCurrentDateComment(): String{
        val date = Date()
        return commentDateFormat.format(date)
    }

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
        val current = Date().time
        val created = serverDateFormat.parse(date)
        var timeDisplayed = ""

        try {
            var diffTimeWithSecond = (current - created.time) / MILLI_SECOND
            val minutes = diffTimeWithSecond / SECOND
            val hours = minutes /MINUTE
            val days = hours / HOUR
            val month = days / DAY
            val year = month / MONTH

            timeDisplayed = when {
                diffTimeWithSecond < SECOND -> {
                    "${diffTimeWithSecond}초 전"
                }
                minutes < MINUTE -> {
                    "${minutes}분 전"
                }
                hours < HOUR -> {
                    "${hours}시간 전"
                }
                days < DAY -> {
                    "${days}일 전"
                }
                month < MONTH -> {
                    "${month}달 전"
                }
                else -> {
                    "${year}년 전"
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return timeDisplayed
    }

    private const val MILLI_SECOND = 1000
    private const val SECOND = 60
    private const val MINUTE = 60
    private const val HOUR = 24
    private const val DAY = 30
    private const val MONTH = 12
}