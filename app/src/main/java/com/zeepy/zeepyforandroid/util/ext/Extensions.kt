package com.zeepy.zeepyforandroid.util.ext

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.building.BuildingDealDTO
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragment
import com.zeepy.zeepyforandroid.review.data.dto.ResponseReviewDTO
import java.lang.RuntimeException

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

fun NavController.isOnBackStack(@IdRes id: Int): Boolean =
    try {
        getBackStackEntry(id); true
    } catch (e: Throwable) {
        false
    }

fun List<BuildingDealDTO>.hasDealType(dealType: String): Boolean {
    try {
        this.forEach {
            if (it.dealType == dealType) {
                return true
            }
        }
    } catch (e: Throwable) {
        return false
    }
    return false
}

fun List<BuildingDealDTO>.isWithinCost(monthly: Boolean, mMinCost: Int?, mMaxCost: Int?, deposit: Boolean, dMinCost: Int?, dMaxCost: Int?) : Boolean {
    var result = true
    try {
        if (monthly && !deposit) {
            run {
                this.forEach {
                    if (it.monthlyRent >= mMinCost!! && it.monthlyRent <= mMaxCost!!) {
                        return@forEach
                    } else {
                        result = false
                        return@run
                    }
                }
            }
        } else if (!monthly && deposit) {
            run {
                this.forEach {
                    if (it.deposit >= dMinCost!! && it.deposit <= dMaxCost!!) {
                        return@forEach
                    } else {
                        result = false
                        return@run
                    }
                }
            }
        }
        return result
    } catch (e: Throwable) {
        e.printStackTrace()
        return false
    }
}

// options를 모두 포함해야만 true 리턴
fun List<ResponseReviewDTO>.hasOptions(options: List<String>): Boolean {
    var result = false
    try {
        this.forEach loop1@ {
            run {
                options.forEach { option ->
                    if (!it.furnitures.contains(option)) {
                        return@run
                    }
                }
                result = true
                return result
            }
        }
    } catch (e: Throwable) {
        return false
    }
    return result
}

