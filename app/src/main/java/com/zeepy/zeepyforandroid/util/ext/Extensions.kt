package com.zeepy.zeepyforandroid.util.ext

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager

const val MAPQUE_PREF = "mapque_pref"

fun Context.sharedPreferences(prefName: String = MAPQUE_PREF): SharedPreferences =
    getSharedPreferences(prefName, Context.MODE_PRIVATE)

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view,  InputMethodManager.SHOW_IMPLICIT)
}


