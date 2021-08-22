package com.zeepy.zeepyforandroid.di

import com.zeepy.zeepyforandroid.address.datasource.AddressDataSource
import com.zeepy.zeepyforandroid.address.datasource.AddressDataSourceImpl
import com.zeepy.zeepyforandroid.address.datasource.NotAuthSearchAddressDataSource
import com.zeepy.zeepyforandroid.address.datasource.NotAuthSearchAddressDataSourceImpl
import com.zeepy.zeepyforandroid.community.data.remote.datasource.PostingListDataSource
import com.zeepy.zeepyforandroid.community.data.remote.datasource.PostingListDataSourceImpl
import com.zeepy.zeepyforandroid.map.datasource.RemoteBuildingsDataSource
import com.zeepy.zeepyforandroid.map.datasource.RemoteBuildingsDataSourceImpl
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.qualifier.UnAuthService
import com.zeepy.zeepyforandroid.qualifier.ZeepyService
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
    fun providePostingListDataSource(@ZeepyService zeepyApiService: ZeepyApiService): PostingListDataSource = PostingListDataSourceImpl(zeepyApiService)

    @Provides
    @Singleton
    fun provideAddressDataSource(@ZeepyService zeepyApiService: ZeepyApiService): AddressDataSource = AddressDataSourceImpl(zeepyApiService)

    @Provides
    @Singleton
    fun provideNotAuthSearchAddressDataSource(@UnAuthService zeepyApiService: ZeepyApiService): NotAuthSearchAddressDataSource = NotAuthSearchAddressDataSourceImpl(zeepyApiService)

    @Provides
    @Singleton
    fun provideBuildingsDataSource(@ZeepyService zeepyApiService: ZeepyApiService): RemoteBuildingsDataSource = RemoteBuildingsDataSourceImpl(zeepyApiService)
}