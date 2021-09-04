package com.zeepy.zeepyforandroid.myprofile.repository

interface MyProfileRepository {
    suspend fun submitWithdrawal(): Unit?
    suspend fun logout(): Unit?
}