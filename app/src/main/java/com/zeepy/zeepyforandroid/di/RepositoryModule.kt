package com.zeepy.zeepyforandroid.di

import com.zeepy.zeepyforandroid.address.repository.SearchAddressListRepository
import com.zeepy.zeepyforandroid.address.repository.SearchAddressListRepositoryImpl
import com.zeepy.zeepyforandroid.address.datasource.NotAuthSearchAddressDataSource
import com.zeepy.zeepyforandroid.community.data.remote.datasource.PostingListDataSource
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepository
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepositoryImpl
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

    @Provides
    @Singleton
    fun provideAddressListRepository(dataSource: NotAuthSearchAddressDataSource): SearchAddressListRepository = SearchAddressListRepositoryImpl(dataSource)
}