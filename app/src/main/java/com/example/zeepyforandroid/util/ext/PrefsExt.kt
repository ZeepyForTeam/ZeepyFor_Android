package com.example.zeepyforandroid.util.ext

import android.content.Context
import android.content.SharedPreferences

const val MAPQUE_PREF = "mapque_pref"

fun Context.sharedPreferences(prefName: String = MAPQUE_PREF) : SharedPreferences =
    getSharedPreferences(prefName, Context.MODE_PRIVATE)