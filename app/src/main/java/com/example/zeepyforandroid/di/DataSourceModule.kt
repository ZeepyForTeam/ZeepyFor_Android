package com.example.zeepyforandroid.di

import com.example.zeepyforandroid.review.data.source.LessorPersonalityDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesLessorPersonalityDataSource(): LessorPersonalityDataSource = LessorPersonalityDataSource()
}