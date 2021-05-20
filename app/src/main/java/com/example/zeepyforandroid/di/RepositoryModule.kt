package com.example.zeepyforandroid.di

import com.example.zeepyforandroid.community.data.remote.data.PostingListDataSource
import com.example.zeepyforandroid.community.data.repository.PostingListRepository
import com.example.zeepyforandroid.community.data.repository.PostingListRepositoryImpl
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