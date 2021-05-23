package com.example.zeepyforandroid.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.IllegalArgumentException
import javax.inject.Inject

class SharedUtil @Inject constructor(
   context: Context
) {
    private val ENCRYPTED_PREFS = "zeepy_encrypted_prefs"

    val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        ENCRYPTED_PREFS,
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
//
//    fun putString(key: String, value: String) {
//        encryptedSharedPreferences.edit().putString(key, value).apply()
//    }
//
//    fun getString(key: String) = encryptedSharedPreferences.getString(key, "not available")


    fun <T> putSharedPref(key: String, value: T) = with(encryptedSharedPreferences.edit()) {
        when(value) {
            is String -> putString(key, value).apply()
            is Long -> putLong(key, value).apply()
            is Int -> putInt(key, value).apply()
            is Boolean -> putBoolean(key, value).apply()
            is Float -> putFloat(key, value).apply()
            else -> IllegalArgumentException("Preferences type error")
        }
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