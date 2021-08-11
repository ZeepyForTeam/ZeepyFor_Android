package com.zeepy.zeepyforandroid.eunm

import androidx.annotation.StringRes
import com.zeepy.zeepyforandroid.R
import java.lang.IllegalArgumentException

enum class TotalEvaluation(@StringRes val evaluation: Int) {
    GOOD(R.string.total_good),
    SOSO(R.string.total_recommendation),
    BAD(R.string.total_no_recommendation);

    companion object {
        fun findTotalEvaluation(evaluation: Int): String {
            return values().find { it.evaluation == evaluation }?.name ?: throw IllegalArgumentException("Total Evaluation Not Matched")
        }
    }
}