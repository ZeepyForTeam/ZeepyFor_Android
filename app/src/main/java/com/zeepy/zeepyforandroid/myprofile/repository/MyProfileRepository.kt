package com.zeepy.zeepyforandroid.myprofile.repository

import com.zeepy.zeepyforandroid.myprofile.data.SimpleReviewDTOList

interface MyProfileRepository {
    suspend fun submitWithdrawal(): Unit?
    suspend fun logout(): Unit?
    suspend fun getUserReviews(): SimpleReviewDTOList
}