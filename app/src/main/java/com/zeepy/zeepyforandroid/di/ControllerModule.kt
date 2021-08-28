package com.zeepy.zeepyforandroid.di

import com.zeepy.zeepyforandroid.address.controller.AddressController
import com.zeepy.zeepyforandroid.address.controller.AddressControllerImpl
import com.zeepy.zeepyforandroid.community.controller.WritePostingController
import com.zeepy.zeepyforandroid.community.controller.WritePostingControllerImpl
import com.zeepy.zeepyforandroid.imagecontrol.ImageController
import com.zeepy.zeepyforandroid.imagecontrol.ImageControllerImpl
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.controller.TokenController
import com.zeepy.zeepyforandroid.network.auth.controller.TokenControllerImpl
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.qualifier.UnAuthService
import com.zeepy.zeepyforandroid.qualifier.ZeepyService
import com.zeepy.zeepyforandroid.review.PostReviewController
import com.zeepy.zeepyforandroid.review.PostReviewControllerImpl
import com.zeepy.zeepyforandroid.signin.controller.SignInController
import com.zeepy.zeepyforandroid.signin.controller.SignInControllerImpl
import com.zeepy.zeepyforandroid.signup.controller.SignUpController
import com.zeepy.zeepyforandroid.signup.controller.SignUpControllerImpl
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
    fun providePostReviewController(@ZeepyService zeepyApiService: ZeepyApiService): PostReviewController =
        PostReviewControllerImpl(zeepyApiService)

    @Provides
    @Singleton
    fun provideSignUpController(@UnAuthService zeepyApiService: ZeepyApiService): SignUpController =
        SignUpControllerImpl(zeepyApiService)

    @Provides
    @Singleton
    fun provideSignInController(@UnAuthService zeepyApiService: ZeepyApiService): SignInController =
        SignInControllerImpl(zeepyApiService)

    @Provides
    @Singleton
    fun provideAuthController(
        @UnAuthService zeepyApiService: ZeepyApiService,
        userPreferenceManager: UserPreferenceManager
    ): TokenController = TokenControllerImpl(zeepyApiService, userPreferenceManager)

    @Provides
    @Singleton
    fun provideAddressController(@ZeepyService zeepyApiService: ZeepyApiService): AddressController =
        AddressControllerImpl(zeepyApiService)

    @Provides
    @Singleton
    fun provideWritePostingController(@ZeepyService zeepyApiService: ZeepyApiService): WritePostingController =
        WritePostingControllerImpl(zeepyApiService)

    @Provides
    @Singleton
    fun provideImageController(@ZeepyService zeepyApiService: ZeepyApiService): ImageController =
        ImageControllerImpl(zeepyApiService)
}