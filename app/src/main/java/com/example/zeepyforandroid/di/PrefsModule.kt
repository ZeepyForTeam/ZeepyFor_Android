package com.example.zeepyforandroid.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.zeepyforandroid.preferences.SharedPreferencesManager
import com.example.zeepyforandroid.preferences.UserPreferenceManager
import com.example.zeepyforandroid.util.ext.sharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PrefsModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        EncryptedSharedPreferences.create(
            "zeepy_encrypted_prefs",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    @Provides
    @Singleton
    fun provideSharedUtil(prefs: SharedPreferences): SharedPreferencesManager = SharedPreferencesManager(prefs)

    @Provides
    @Singleton
    fun provideUserPreferenceManager(sharedPreferencesManager: SharedPreferencesManager): UserPreferenceManager = UserPreferenceManager(sharedPreferencesManager)
}