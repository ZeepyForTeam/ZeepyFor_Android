package com.zeepy.zeepyforandroid.util

import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class WidthResizeAnimation(view: View, targetWidth: Int, fillParent: Boolean) :
    Animation() {
    var targetWidth: Int
    var originalWidth: Int
    var view: View
    private var expand = false
    private var newWidth = 0
    private var fillParent: Boolean
    override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        if (expand && newWidth < targetWidth) {
            newWidth = (newWidth + (targetWidth - newWidth) * interpolatedTime).toInt()
        }
        if (!expand && newWidth > targetWidth) {
            newWidth = (newWidth - (newWidth - targetWidth) * interpolatedTime).toInt()
        }
        if (fillParent && interpolatedTime.toDouble() == 1.0) {
            view.layoutParams.width = -1
        } else {
            view.layoutParams.width = newWidth
        }
        view.requestLayout()
    }

    override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
        super.initialize(width, height, parentWidth, parentHeight)
    }

    override fun willChangeBounds(): Boolean {
        return true
    }

    init {
        this.view = view
        originalWidth = this.view.measuredWidth
        this.targetWidth = targetWidth
        newWidth = originalWidth
        expand = originalWidth <= targetWidth
        this.fillParent = fillParent
    }
}