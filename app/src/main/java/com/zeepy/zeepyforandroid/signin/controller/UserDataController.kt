package com.zeepy.zeepyforandroid.signin.controller

import com.zeepy.zeepyforandroid.signin.dto.response.ResponseNicknameAndEmailDTO
import io.reactivex.rxjava3.core.Single

interface UserDataController {
    fun getNicknameAndEmail(email: String): Single<ResponseNicknameAndEmailDTO>
}