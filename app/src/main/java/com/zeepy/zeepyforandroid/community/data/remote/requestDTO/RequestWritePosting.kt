package com.zeepy.zeepyforandroid.community.data.remote.requestDTO

data class RequestWritePosting(
    val address: String,
    val communityCategory: String,
    val content: String?,
    val imageUrls: List<String?>,
    val instructions: String?,
    val productName: String?,
    val productPrice: String?,
    val purchasePlace: String?,
    val sharingMethod: String?,
    val targetNumberOfPeople: Int?,
    val title: String?
)