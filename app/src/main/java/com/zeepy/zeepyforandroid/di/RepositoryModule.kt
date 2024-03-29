package com.zeepy.zeepyforandroid.di

import com.zeepy.zeepyforandroid.address.repository.SearchAddressListRepository
import com.zeepy.zeepyforandroid.address.repository.SearchAddressListRepositoryImpl
import com.zeepy.zeepyforandroid.address.datasource.SearchAddressDataSource
import com.zeepy.zeepyforandroid.community.data.remote.datasource.PostingListDataSource
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepository
import com.zeepy.zeepyforandroid.community.data.repository.PostingListRepositoryImpl
import com.zeepy.zeepyforandroid.lookaround.datasource.RemoteBuildingDataSource
import com.zeepy.zeepyforandroid.lookaround.repository.BuildingRepository
import com.zeepy.zeepyforandroid.lookaround.repository.BuildingRepositoryImpl
import com.zeepy.zeepyforandroid.map.datasource.RemoteBuildingsDataSource
import com.zeepy.zeepyforandroid.map.repository.BuildingsRepository
import com.zeepy.zeepyforandroid.map.repository.BuildingsRepositoryImpl
import com.zeepy.zeepyforandroid.myprofile.datasource.MyProfileRemoteDataSource
import com.zeepy.zeepyforandroid.myprofile.repository.MyProfileRepository
import com.zeepy.zeepyforandroid.myprofile.repository.MyProfileRepositoryImpl
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
    fun provideAddressListRepository(dataSource: SearchAddressDataSource): SearchAddressListRepository = SearchAddressListRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideBuildingsRepository(dataSource: RemoteBuildingsDataSource): BuildingsRepository = BuildingsRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideMyProfileRepository(dataSource: MyProfileRemoteDataSource): MyProfileRepository = MyProfileRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideLookAroundBuildingsRepository(dataSource: RemoteBuildingDataSource): BuildingRepository = BuildingRepositoryImpl(dataSource)
}