package com.zeepy.zeepyforandroid.signin.controller

import com.zeepy.zeepyforandroid.network.auth.dto.ResponseAuthDTO
import com.zeepy.zeepyforandroid.signin.dto.request.RequestLoginDTO
import com.zeepy.zeepyforandroid.signin.dto.request.RequestSocialSigninDTO
import com.zeepy.zeepyforandroid.signin.dto.response.ResponseNicknameAndEmailDTO
import io.reactivex.Single

interface UserDataController {
    fun getNicknameAndEmail(email: String): Single<ResponseNicknameAndEmailDTO>
}