package com.example.zeepyforandroid.util

import androidx.annotation.FontRes
import com.example.zeepyforandroid.R

enum class FontType(@FontRes val fontRes: Int) {
    FONT_LIGHT(R.font.nanum_square_round_light),
    FONT_REGULAR(R.font.nanum_square_round_regular),
    FONT_EXTRABOLD(R.font.nanum_square_round_extrabold),
    FONT_BOLD(R.font.nanum_square_round_bold);
}