package com.zeepy.zeepyforandroid.util

import android.app.Activity
import android.os.Build
import android.util.DisplayMetrics
import net.daum.mf.map.api.MapPoint
import kotlin.math.*


class MapHelper(
    val activity: Activity, var zoomLevel: Int, var centerPoint: MapPoint, var mapDisplayOffset: Float
) {
    var displayWidth: Int = 0
    var displayHeight: Int = 0
    var range = 2.0.pow((zoomLevel).toDouble() + 1)
    var topLeftLat: Double = 0.0
    var topLeftLng: Double = 0.0
    var topRightLat: Double = 0.0
    var topRightLng: Double = 0.0
    var bottomRightLat: Double = 0.0
    var bottomRightLng: Double = 0.0
    var bottomLeftLat: Double = 0.0
    var bottomLeftLng: Double = 0.0

    fun getPointLatLng(x: Int, y: Int): Pair<Double, Double> {
        val parallelMultiplier = cos(centerPoint.mapPointGeoCoord.latitude * PI / 180)
        val degreesPerPixelX = 360 / 2.0.pow(28 - zoomLevel)
        val degreesPerPixelY = 360 / 2.0.pow(28 - zoomLevel) * parallelMultiplier

        return Pair(
            centerPoint.mapPointGeoCoord.latitude - degreesPerPixelY * 1.5 * (y - displayHeight / 2),
            centerPoint.mapPointGeoCoord.longitude + degreesPerPixelX * 1.5 * (x - displayWidth / 2)
        )
    }

    fun setFourPoints() {
        topLeftLat = getPointLatLng(0, 0).first
        topLeftLng = getPointLatLng(0, 0).second

        topRightLat = getPointLatLng(displayWidth, 0).first
        topRightLng = getPointLatLng(displayWidth, 0).second

        bottomLeftLat = getPointLatLng(0, displayHeight).first
        bottomLeftLng = getPointLatLng(0, displayHeight).second

        bottomRightLat = getPointLatLng(displayWidth, displayHeight).first
        bottomRightLng = getPointLatLng(displayWidth, displayHeight).second
    }

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
        displayHeight = metrics.heightPixels - mapDisplayOffset.roundToInt()
    }

}