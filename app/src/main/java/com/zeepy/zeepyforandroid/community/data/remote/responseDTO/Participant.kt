package com.zeepy.zeepyforandroid.community.data.remote.responseDTO

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Participant(
    val id: Int,
    val user: User
):Parcelable