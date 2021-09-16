package com.zeepy.zeepyforandroid.community.data.remote.requestDTO

data class RequestReportDTO(
    val description: String,
    val reportId: Int,
    val reportType: String,
    val reportUser: Int,
    val targetTableType: String,
    val targetUser: Int
)