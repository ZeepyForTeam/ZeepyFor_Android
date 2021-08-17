package com.zeepy.zeepyforandroid.di

import com.zeepy.zeepyforandroid.community.data.remote.datasource.PostingListDataSource
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepository
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepositoryImpl
import com.zeepy.zeepyforandroid.localdata.ZeepyDatabase
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepository
import com.zeepy.zeepyforandroid.localdata.ZeepyLocalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePostingListRepository(dataSource: PostingListDataSource): PostingListRepository = PostingListRepositoryImpl(dataSource)

}