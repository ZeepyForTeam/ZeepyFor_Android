package com.example.zeepyforandroid.util

import androidx.annotation.StringRes
import com.example.zeepyforandroid.R

enum class ReviewNotice(@StringRes val text: Int, val map: Map<Int, Int>) {
    SELECT_ADDRESS(R.string.select_address, mapOf(0 to 9)),
    CHECK_LESSOR_PERSONALITY(R.string.lessor_personality, mapOf(0 to 3, 5 to 10)),
    WRITE_LESSOR_DETAIL(R.string.lessor_detail_info, mapOf(0 to 3, 13 to 19)),
    WRITE_DETAIL_ADDRESS(R.string.write_detail_address, mapOf(0 to 4)),
    CHECK_HOUSE_CONDITION(R.string.house_condition, mapOf(0 to 8, 15 to 20)),
    LOAD_HOUSE_PICTURE(R.string.lessor_house_picture, mapOf(3 to 11))
}