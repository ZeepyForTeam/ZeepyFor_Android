package com.example.zeepyforandroid.di

import com.example.zeepyforandroid.community.data.remote.datasource.PostingListDataSource
import com.example.zeepyforandroid.community.data.remote.datasource.PostingListDataSourceImpl
import com.example.zeepyforandroid.network.ZeepyApiService
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
    fun providePostingListDataSource(zeepyApiService: ZeepyApiService): PostingListDataSource = PostingListDataSourceImpl(zeepyApiService)

//    @Provides
//    @Singleton
//    fun provideGeocodingDataSource(naverApiService: NaverApiService) : GeocodingDataSource {
//        return GeocodingRepository(naverApiService)
//    }
}