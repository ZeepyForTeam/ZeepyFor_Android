package com.example.zeepyforandroid.di

import com.example.zeepyforandroid.community.data.remote.data.PostingListDataSource
import com.example.zeepyforandroid.community.data.remote.data.PostingListDataSourceImpl
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
    fun providePostingListDataSource(): PostingListDataSource = PostingListDataSourceImpl()

//    @Provides
//    @Singleton
//    fun provideGeocodingDataSource(naverApiService: NaverApiService) : GeocodingDataSource {
//        return GeocodingRepository(naverApiService)
//    }
}