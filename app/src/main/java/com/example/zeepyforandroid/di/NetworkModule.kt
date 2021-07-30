package com.example.zeepyforandroid.di


import com.example.zeepyforandroid.preferences.UserPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(userPreferenceManager: UserPreferenceManager): OkHttpClient {
        val baseClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()

        val interceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder().addHeader("token", userPreferenceManager.getUserAccessToken())
                .build()
            chain.proceed(request)
        }
        return baseClient.newBuilder().addNetworkInterceptor(interceptor).build()
    }

}