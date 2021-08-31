package com.zeepy.zeepyforandroid.myprofile.datasource

interface MyProfileRemoteDataSource {
    suspend fun submitWithdrawal(): Unit?
    suspend fun logout(): Unit?
}