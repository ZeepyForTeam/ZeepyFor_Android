package com.example.zeepyforandroid

import com.example.zeepyforandroid.review.LessorPersonalityDataSource
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