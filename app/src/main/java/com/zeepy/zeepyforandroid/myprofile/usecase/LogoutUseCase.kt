package com.zeepy.zeepyforandroid.myprofile.usecase

import com.zeepy.zeepyforandroid.di.IoDispatcher
import com.zeepy.zeepyforandroid.map.usecase.util.CoroutineUseCase
import com.zeepy.zeepyforandroid.myprofile.repository.MyProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: MyProfileRepository,
    @IoDispatcher coroutineDispatcher: CoroutineDispatcher
) : CoroutineUseCase<LogoutUseCase.Params, Unit?>(coroutineDispatcher) {

    override suspend fun execute(parameter: Params): Unit? {
        return repository.logout(parameter.userEmail)
    }

    data class Params(
        val userEmail: String,
    )
}