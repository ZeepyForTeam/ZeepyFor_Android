package com.zeepy.zeepyforandroid.util

sealed class NetworkStatus<T> (val status: State, var data:T? = null, val message: String? = null) {
    class SUCCESS<T>(data: T): NetworkStatus<T>(State.SUCCESS, data)
    class ERROR<T>(data: T? = null, message: String): NetworkStatus<T>(State.ERROR,data, message)
    class LOADING<T>(data: T? = null): NetworkStatus<T>(State.LOADING, data, null)

    enum class State{
        SUCCESS,
        ERROR,
        LOADING
    }
}
