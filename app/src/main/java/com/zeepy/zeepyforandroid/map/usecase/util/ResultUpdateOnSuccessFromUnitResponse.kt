package com.zeepy.zeepyforandroid.map.usecase.util

import androidx.lifecycle.MutableLiveData

inline fun <T, R> Result<R?>.updateOnSuccessFromUnitResponse(liveData: MutableLiveData<T?>, onNull: T, onError: (error: Throwable) -> Unit) {

    //For Unit Responses
    if (data == null) {
        liveData.value = onNull
    } else {
        when (this) {
            is Result.Success -> {
                if (this.data == null) {
                    liveData.value = onNull
                }
            }
            is Result.Error -> {
                onError(this.exception)
            }
        }
    }
}