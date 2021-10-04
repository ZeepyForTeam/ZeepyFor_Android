package com.zeepy.zeepyforandroid.myprofile.repository

import com.zeepy.zeepyforandroid.myprofile.data.ModifyPasswordReqDTO
import com.zeepy.zeepyforandroid.myprofile.data.ReportRequestDTO
import com.zeepy.zeepyforandroid.myprofile.data.SimpleReviewDTOList
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

    override suspend fun getUserReviews(): SimpleReviewDTOList {
        return dataSource.getUserReviews()
    }

    override suspend fun changeUserPassword(modifyPasswordReqDTO: ModifyPasswordReqDTO): Unit? {
        return dataSource.changeUserPassword(modifyPasswordReqDTO);
    }
}