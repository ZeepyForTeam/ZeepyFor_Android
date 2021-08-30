package com.zeepy.zeepyforandroid.myprofile.datasource

interface MyProfileRemoteDataSource {
    suspend fun submitWithdrawal(userEmail: String): Unit?
    suspend fun logout(userEmail: String): Unit?
}