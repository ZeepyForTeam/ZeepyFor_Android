package com.example.zeepyforandroid.di

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.zeepyforandroid.util.SharedUtil
import com.example.zeepyforandroid.util.ext.sharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EncryptedSharedPrefs


@Module
@InstallIn(ActivityRetainedComponent::class)
object PrefsModule {

    @Provides
    @ActivityRetainedScoped
    fun providePrefs(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.sharedPreferences()
    }

    @Provides
    @ActivityRetainedScoped
    @EncryptedSharedPrefs
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        EncryptedSharedPreferences.create(
            "zeepy_encrypted_prefs",
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    //ActivityRetainedScoped로 설정하면 이슈가 없을지 고민,,,
    @Provides
    @ActivityRetainedScoped
    fun provideSharedUtil(@EncryptedSharedPrefs prefs: SharedPreferences): SharedUtil = SharedUtil(prefs)
}