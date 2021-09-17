package com.zeepy.zeepyforandroid.signin.controller

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.signin.dto.response.ResponseNicknameAndEmailDTO
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserDataControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
) : UserDataController {
    override fun getNicknameAndEmail(email: String): Single<ResponseNicknameAndEmailDTO> =
        zeepyApiService.getUserNicknameAndEmail(email)
}