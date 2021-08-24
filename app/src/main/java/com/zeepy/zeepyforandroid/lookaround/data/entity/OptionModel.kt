package com.zeepy.zeepyforandroid.lookaround.data.entity

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OptionModel(
    @StringRes val option: Int
): Parcelable