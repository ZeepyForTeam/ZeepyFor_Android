package com.zeepy.zeepyforandroid.util

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import net.daum.mf.map.api.MapView
import kotlin.math.pow


class MapHelper(val activity: Activity, mapView: MapView, zoomLevel: Int) {
    var displayWidth: Int = 0
    var displayHeight: Int = 0
    var initLat = 0.0000005
    var initLng = 0.0000008
    var range = 2.0.pow((zoomLevel + 1).toDouble())
    private val centerPoint = mapView.mapCenterPoint.mapPointGeoCoord
    var topLeftLat: Double = 0.0
    var topLeftLng: Double = 0.0
    var topRightLat: Double = 0.0
    var topRightLng: Double = 0.0
    var bottomRightLat: Double = 0.0
    var bottomRightLng: Double = 0.0
    var bottomLeftLat: Double = 0.0
    var bottomLeftLng: Double = 0.0


    fun setMapMetrics() {
        val metrics = DisplayMetrics()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val display = activity.display
            display?.getRealMetrics(metrics)
        } else {
            @Suppress("DEPRECATION")
            val display = activity.windowManager.defaultDisplay
            @Suppress("DEPRECATION")
            display.getMetrics(metrics)
        }

        displayWidth = metrics.widthPixels
        displayHeight = metrics.heightPixels
    }

    fun setTopLeftPoint() {
        topLeftLat = centerPoint.latitude + (initLat * displayWidth * 1.5 * range)
        topLeftLng = centerPoint.longitude + (initLng * displayHeight * 1.5 * range)
    }

    fun setBottomRightPoint() {
        bottomRightLat = centerPoint.latitude - (topLeftLat - centerPoint.latitude)
        bottomRightLng = centerPoint.longitude - (topLeftLng - centerPoint.longitude)
    }

    fun setRemainingPoints() {
        topRightLat = bottomRightLat
        topRightLng = topLeftLng
        bottomLeftLat = topLeftLat
        bottomLeftLng = bottomRightLng
    }



}