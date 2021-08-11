package com.zeepy.zeepyforandroid.di

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.review.PostReviewController
import com.zeepy.zeepyforandroid.review.PostReviewControllerImpl
import com.zeepy.zeepyforandroid.signup.SignUpController
import com.zeepy.zeepyforandroid.signup.SignUpControllerImpl
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

    @Provides
    @Singleton
    fun provideSignUpController(zeepyApiService: ZeepyApiService): SignUpController = SignUpControllerImpl(zeepyApiService)
}