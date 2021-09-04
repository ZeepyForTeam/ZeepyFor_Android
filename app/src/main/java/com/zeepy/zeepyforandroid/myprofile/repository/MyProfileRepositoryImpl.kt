package com.zeepy.zeepyforandroid.myprofile.repository

import com.zeepy.zeepyforandroid.myprofile.datasource.MyProfileRemoteDataSource
import javax.inject.Inject

class MyProfileRepositoryImpl @Inject constructor(
    private val dataSource: MyProfileRemoteDataSource
): MyProfileRepository {
    override suspend fun submitWithdrawal(): Unit? {
        return dataSource.submitWithdrawal()
    }

    override suspend fun logout(): Unit? {
        return dataSource.logout()
    }
}