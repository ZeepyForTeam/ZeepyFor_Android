package com.example.zeepyforandroid.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

//    @Provides
//    @Singleton
//    fun provideGeocodingDataSource(naverApiService: NaverApiService) : GeocodingDataSource {
//        return GeocodingRepository(naverApiService)
//    }

}