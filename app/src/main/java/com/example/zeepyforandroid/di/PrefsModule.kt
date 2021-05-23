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

    //ActivityRetainedScoped로 설정하면 이슈가 없을지 고민,,,
    @Provides
    @ActivityRetainedScoped
    fun provideSharedUtil(@ApplicationContext context: Context) = SharedUtil(context)
}