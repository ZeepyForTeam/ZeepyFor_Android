package com.zeepy.zeepyforandroid.util

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics


object MetricsConverter {
    /**
     * @param dp A value in dp (density independent pixels) unit.
     * @param context Context to get resources and device specific display metrics. If you don't have
     * access to Context, pass null.
     */
    fun dpToPixel(dp: Float, context: Context?): Float {
        return if (context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        } else {
            val metrics = Resources.getSystem().displayMetrics
            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }

    /**
     * @param px A value in px (pixels) unit.
     * @param context Context to get resources and device specific display metrics. If you don't have
     * access to Context, pass null.
     */
    fun pixelsToDp(px: Float, context: Context?): Float {
        return if (context != null) {
            val resources = context.resources
            val metrics = resources.displayMetrics
            px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        } else {
            val metrics = Resources.getSystem().displayMetrics
            px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        }
    }
}