package com.example.zeepyforandroid.map.data

interface ResultListener<T> {
    fun success(result: T)
    fun fail(msg: String? = null)
}