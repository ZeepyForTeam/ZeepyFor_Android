package com.zeepy.zeepyforandroid.community.data.remote.responseDTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val name: String,
    val profileImage: String
): Parcelable