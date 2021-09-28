package com.zeepy.zeepyforandroid.myprofile.data

data class ReportRequestDTO(
    val description: String,
    val reportId: Int,
    val reportType: String,
    val reportUser: Int,
    val targetTableType: String,
    val targetUser: Int
)
