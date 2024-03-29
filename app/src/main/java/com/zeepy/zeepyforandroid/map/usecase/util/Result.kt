package com.zeepy.zeepyforandroid.map.usecase.util

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null

// TODO: Originally, data null exceptions were handled in get() but were removed for Unit? Responses to go through
val <T> Result<T>.data
    get() = (this as? Result.Success)?.data