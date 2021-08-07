package com.example.zeepyforandroid.community.data.entity

//Todo: 서버 나오면 이것도 고치자.....
data class PostingListModel(
    val postingType: String,
    val status: Boolean,
    val title: String,
    val content: String,
    val time: String
)