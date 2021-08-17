package com.zeepy.zeepyforandroid.conditionsearch.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchEntry(
    var buildingTypes: Map<String, CheckBoxModel>,
    var tradeTypes: Map<String, CheckBoxModel>,
    var depositPaymentStart: Int,
    var depositPaymentEnd: Int,
    var monthlyPaymentStart: Int,
    var monthlyPaymentEnd: Int
): Parcelable
