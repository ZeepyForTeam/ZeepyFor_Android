package com.zeepy.zeepyforandroid.customview

import android.view.View

interface DialogClickListener {
    fun clickLeftButton(dialog: ZeepyDialog)
    fun clickRightButton(dialog: ZeepyDialog)
}