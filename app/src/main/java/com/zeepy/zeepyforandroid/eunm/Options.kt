package com.zeepy.zeepyforandroid.eunm

import androidx.annotation.StringRes
import com.zeepy.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class Options(@StringRes val furniture: Int) {
    AIRCONDITIONAL(R.string.airconditional),
    WASHINGMACHINE(R.string.wasingmachine),
    BED(R.string.bed),
    CLOSET(R.string.closet),
    DESK(R.string.desk),
    REFRIDGERATOR(R.string.refridgerator),
    INDUCTION(R.string.induction),
    BURNER(R.string.burner),
    MICROWAVE(R.string.microwave);

    companion object {
        fun findOptions(furniture: Int): String {
            return values().find { it.furniture == furniture }?.name ?: throw IllegalArgumentException("Furniture Not Found")
        }
    }
}