package com.zeepy.zeepyforandroid.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.zeepy.zeepyforandroid.localdata.ZeepyDao
import com.zeepy.zeepyforandroid.localdata.ZeepyDatabase
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app

    @Provides
    @Singleton
    fun provideZeepyLocalDatabase(@ApplicationContext context: Context): ZeepyDatabase =
        Room.databaseBuilder(context, ZeepyDatabase::class.java, "zeepy_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideZeepyDao(zeepyDatabase: ZeepyDatabase): ZeepyDao = zeepyDatabase.getzeepyDao()

    @Provides
    @Singleton
    fun provideZeepyLocalRepository(database: ZeepyDatabase): ZeepyLocalRepository = ZeepyLocalRepositoryImpl(database)

}