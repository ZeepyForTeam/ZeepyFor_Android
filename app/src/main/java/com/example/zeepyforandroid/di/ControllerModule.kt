package com.example.zeepyforandroid.di

import com.example.zeepyforandroid.network.ZeepyApiService
import com.example.zeepyforandroid.review.PostReviewController
import com.example.zeepyforandroid.review.PostReviewControllerImpl
import com.example.zeepyforandroid.signup.SignUpController
import com.example.zeepyforandroid.signup.SignUpControllerImpl
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