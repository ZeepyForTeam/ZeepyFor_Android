package com.zeepy.zeepyforandroid.di

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.qualifier.UnAuthService
import com.zeepy.zeepyforandroid.qualifier.ZeepyService
import com.zeepy.zeepyforandroid.review.PostReviewController
import com.zeepy.zeepyforandroid.review.PostReviewControllerImpl
import com.zeepy.zeepyforandroid.signin.SignInController
import com.zeepy.zeepyforandroid.signin.SignInControllerImpl
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
    fun providePostReviewController(@ZeepyService zeepyApiService: ZeepyApiService): PostReviewController = PostReviewControllerImpl(zeepyApiService)

    @Provides
    @Singleton
    fun provideSignUpController(@UnAuthService zeepyApiService: ZeepyApiService): SignUpController = SignUpControllerImpl(zeepyApiService)

    @Provides
    @Singleton
    fun provideSignInController(@UnAuthService zeepyApiService: ZeepyApiService): SignInController = SignInControllerImpl(zeepyApiService)
}