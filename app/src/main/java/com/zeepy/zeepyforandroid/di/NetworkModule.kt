package com.zeepy.zeepyforandroid.di


import android.content.Context
import com.nhn.android.naverlogin.OAuthLogin
import com.zeepy.zeepyforandroid.BuildConfig
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.network.KakaoApiService
import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.AuthInterceptor
import com.zeepy.zeepyforandroid.network.auth.controller.TokenController
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.qualifier.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val loggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    @ZeepyOkHttp
    fun provideZeepyOkHttpClientBuilder(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @UnAuthOkHttp
    fun provideAuthOkHttpClientBuilder(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @ZeepyRetrofit
    fun provideZeepyRetrofit(@ZeepyOkHttp okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @UnAuthRetrofit
    fun provideAuthRetrofit(@UnAuthOkHttp okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @ZeepyService
    fun provideZeepyApiService(@ZeepyRetrofit retrofit: Retrofit): ZeepyApiService =
        retrofit.create(ZeepyApiService::class.java)


    @Provides
    @Singleton
    @UnAuthService
    fun provideUnAuthApiService(@UnAuthRetrofit retrofit: Retrofit): ZeepyApiService =
        retrofit.create(ZeepyApiService::class.java)

//    @Provides
//    @Singleton
//    fun provideAuthenticator(@UnAuthService zeepyApiService: ZeepyApiService, userPreferenceManager: UserPreferenceManager): Authenticator = Authenticator(zeepyApiService, userPreferenceManager)

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        tokenController: TokenController,
        userPreferenceManager: UserPreferenceManager,
        zeepyLocalRepository: ZeepyLocalRepository
    ): AuthInterceptor =
        AuthInterceptor(tokenController, userPreferenceManager, zeepyLocalRepository)

    @Provides
    @Singleton
    fun provideOAuthLogin(@ApplicationContext context: Context): OAuthLogin {
        val mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(
            context,
            BuildConfig.NAVER_CLIENT_ID,
            BuildConfig.NAVER_CLIENT_SECRET_ID,
            "Zeepy"
        )
        return mOAuthLoginInstance
    }

    private val kakaoRetrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(KakaoApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val kakaoApiService: KakaoApiService by lazy {
        kakaoRetrofit
            .build()
            .create(KakaoApiService::class.java)
    }

}