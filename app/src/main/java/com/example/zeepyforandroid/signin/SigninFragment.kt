package com.example.zeepyforandroid.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.BuildConfig
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentSigninBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import io.reactivex.Single
import java.util.*


class SigninFragment : BaseFragment<FragmentSigninBinding>() {
    private lateinit var mOAuthLoginInstance: OAuthLogin
    private val viewModel by viewModels<SigninViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSigninBinding {
        return FragmentSigninBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNaverOAuth()
        loginWithKAKAO()
    }

    private fun initNaverOAuth() {
        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(requireContext(), BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_SECRET_ID, "Zeepy")
        val mOAuthHandler = OAuthHandler()
        binding.buttonNaverLogin.setOAuthLoginHandler(mOAuthHandler)
    }

    private fun loginWithKAKAO() {

        binding.buttonLoginKakao.setOnClickListener {
           checkKakaoToken()
        }
    }

    private fun checkKakaoToken() {
        AuthApiClient.instance.run {
            if (hasToken()) {
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError()) {
                            //access토큰 갱신까지 실패한 것이기 때문에 refresh토큰이 유효하지 않음, 로그인 필요
                            getKakaoLogin()
                        }
                        else {
                            //기타 에러
                        }
                    }
                    else{
                        //토큰 유효성 체크 성공(필요 시 sdk내부에서 토큰 갱신됨)

                    }
                }
            }else {
                //단말에 토큰이 없으니 로그인 필요
                getKakaoLogin()
            }
        }
    }

    private fun getKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                //Login Fail
            }
            else if (token != null) {
                //Login Success
                Log.e("access token", "${token.accessToken}")
                Log.e("access token expire", "${token.accessTokenExpiresAt}")
                Log.e("refresh token", "${token.refreshToken}")
                findNavController().navigate(R.id.action_singInFragment_to_mainFrameFragment)
            }
        }

        UserApiClient.instance.run {
            if(isKakaoTalkLoginAvailable(requireContext())) {
                loginWithKakaoAccount(requireContext(), callback = callback)
            } else {
                loginWithKakaoAccount(requireContext(), callback = callback)
            }
        }
    }

    inner class OAuthHandler: OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if(success) {
                viewModel.changeAccessToken(mOAuthLoginInstance.getAccessToken(requireContext()))
                viewModel.changeRefreshToken(mOAuthLoginInstance.getRefreshToken(requireContext()))
            } else {

            }
        }
    }
}