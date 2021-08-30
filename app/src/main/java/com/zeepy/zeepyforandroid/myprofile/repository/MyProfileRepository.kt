package com.zeepy.zeepyforandroid.myprofile.repository

interface MyProfileRepository {
    suspend fun submitWithdrawal(userEmail: String): Unit?
    suspend fun logout(userEmail: String): Unit?
}