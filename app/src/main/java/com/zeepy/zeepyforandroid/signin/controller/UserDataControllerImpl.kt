package com.zeepy.zeepyforandroid.signin.controller

import com.zeepy.zeepyforandroid.network.ZeepyApiService
import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.signin.dto.request.RequestLoginDTO
import com.zeepy.zeepyforandroid.signin.dto.request.RequestSocialSigninDTO
import com.zeepy.zeepyforandroid.signin.dto.response.ResponseNicknameAndEmailDTO
import io.reactivex.Single
import javax.inject.Inject

class UserDataControllerImpl @Inject constructor(
    private val zeepyApiService: ZeepyApiService
) : UserDataController {
    override fun getNicknameAndEmail(email: String): Single<ResponseNicknameAndEmailDTO> =
        zeepyApiService.getUserNicknameAndEmail(email)
}