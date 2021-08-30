package com.zeepy.zeepyforandroid.myprofile.repository

import com.zeepy.zeepyforandroid.myprofile.datasource.MyProfileRemoteDataSource
import javax.inject.Inject

class MyProfileRepositoryImpl @Inject constructor(
    private val dataSource: MyProfileRemoteDataSource
): MyProfileRepository {
    override suspend fun submitWithdrawal(userEmail: String): Unit? {
        return dataSource.submitWithdrawal(userEmail)
    }

    override suspend fun logout(userEmail: String): Unit? {
        return dataSource.logout(userEmail)
    }
}