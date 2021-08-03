package com.example.zeepyforandroid.di

import com.example.zeepyforandroid.network.ZeepyApiService
import com.example.zeepyforandroid.review.PostReviewController
import com.example.zeepyforandroid.review.PostReviewControllerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ControllerModule {
    @Provides
    @Singleton
    fun providePostReviewController(zeepyApiService: ZeepyApiService):PostReviewController = PostReviewControllerImpl(zeepyApiService)
}