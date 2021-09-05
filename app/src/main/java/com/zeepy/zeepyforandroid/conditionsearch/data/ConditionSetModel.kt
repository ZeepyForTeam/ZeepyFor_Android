package com.zeepy.zeepyforandroid.conditionsearch.data

import com.zeepy.zeepyforandroid.lookaround.data.entity.OptionModel

data class ConditionSetModel(
    val buildingType: String,
    val dealType: String,
    val depositPayStart: Float,
    val depositPayEnd: Float,
    val monthlyPayStart: Float,
    val monthlyPayEnd: Float,
    val options: List<String>
)
