package com.zeepy.zeepyforandroid.preferences

import android.content.SharedPreferences
import java.lang.IllegalArgumentException
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(
    val encryptedSharedPreferences : SharedPreferences
) {
    fun <T> putSharedPref(key: String, value: T) = with(encryptedSharedPreferences.edit()) {
        when(value) {
            is String -> putString(key, value)
            is Long -> putLong(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            else -> IllegalArgumentException("Preferences type error")
        }
        apply()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getSharedPrefs(key: String, defaultValue: T): T  {
        return when(defaultValue) {
            is String-> encryptedSharedPreferences.getString(key, defaultValue) as T
            is Int -> encryptedSharedPreferences.getInt(key, defaultValue) as T
            is Long -> encryptedSharedPreferences.getLong(key, defaultValue) as T
            is Boolean -> encryptedSharedPreferences.getBoolean(key, defaultValue) as T
            is Float -> encryptedSharedPreferences.getFloat(key, defaultValue) as T
            else -> throw IllegalArgumentException("Preferences Type Error")
        }
    }
}