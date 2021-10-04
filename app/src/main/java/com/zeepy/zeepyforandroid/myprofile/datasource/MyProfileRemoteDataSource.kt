package com.zeepy.zeepyforandroid.myprofile.datasource

import com.zeepy.zeepyforandroid.myprofile.data.ModifyPasswordReqDTO
import com.zeepy.zeepyforandroid.myprofile.data.ReportRequestDTO
import com.zeepy.zeepyforandroid.myprofile.data.SimpleReviewDTOList

interface MyProfileRemoteDataSource {
    suspend fun submitWithdrawal(): Unit?
    suspend fun logout(): Unit?
    suspend fun getUserReviews(): SimpleReviewDTOList
    suspend fun changeUserPassword(modifyPasswordReqDTO: ModifyPasswordReqDTO): Unit?
}