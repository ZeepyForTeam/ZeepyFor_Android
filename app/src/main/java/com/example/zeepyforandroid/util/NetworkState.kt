package com.example.zeepyforandroid.util

sealed class NetworkState<T> (val data:T? = null, val message: String? = null) {
    class SUCCESS<T>(data: T): NetworkState<T>(data)
    class ERROR<T>(data: T? = null, message: String): NetworkState<T>(data, message)
    class LOADING<T>(data: T? = null): NetworkState<T>(data, null)
}
