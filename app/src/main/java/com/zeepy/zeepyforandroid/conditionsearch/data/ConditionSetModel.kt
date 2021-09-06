package com.zeepy.zeepyforandroid.conditionsearch.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConditionSetModel(
    val buildingType: String,
    val dealType: String,
    val depositPayStart: Int,
    val depositPayEnd: Int,
    val monthlyPayStart: Int,
    val monthlyPayEnd: Int,
    val options: List<String>
): Parcelable
