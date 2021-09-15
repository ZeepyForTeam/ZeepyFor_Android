package com.zeepy.zeepyforandroid.myprofile.data

data class SimpleReviewDTOList(
    val simpleReviewDtoList: List<SimpleReviewDTO>
)

data class SimpleReviewDTO(
    val address: String,
    val apartmentName: String,
    val communcationTendency: String,
    val id: Int,
    val lessorAge: String,
    val lessorGender: String,
    val lightning: String,
    val pest: String,
    val reviewDate: String,
    val soundInsulation: String,
    val waterPressure: String
)
