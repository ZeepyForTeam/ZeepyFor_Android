package com.example.zeepyforandroid.review.data.dto

import kotlin.properties.Delegates

data class ReviewChoiceModel(
    val item: String,
    var checkedbutton: Int? = -1
) {

}