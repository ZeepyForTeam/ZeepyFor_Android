package com.example.zeepyforandroid.di

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.map.data.geocoding.GeocodingDataSource
import com.example.zeepyforandroid.map.data.geocoding.GeocodingRepository
import com.example.zeepyforandroid.network.api.NaverApiService
import com.example.zeepyforandroid.review.data.source.LessorPersonalityDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun providesLessorPersonalityDataSource(): LessorPersonalityDataSource = LessorPersonalityDataSource()

    @Provides
    @Singleton
    fun provideGeocodingDataSource(naverApiService: NaverApiService) : GeocodingDataSource {
        return GeocodingRepository(naverApiService)
    }
}