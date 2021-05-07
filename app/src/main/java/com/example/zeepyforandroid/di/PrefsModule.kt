package com.example.zeepyforandroid.di

import android.content.Context
import android.content.SharedPreferences
import com.example.zeepyforandroid.application.Hilt_ZeepyApplication
import com.example.zeepyforandroid.util.ext.sharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
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
}