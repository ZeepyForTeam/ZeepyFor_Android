package com.zeepy.zeepyforandroid.signin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.zeepy.zeepyforandroid.BuildConfig
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>(){
    private val viewModel by viewModels<SignInViewModel>()
    @Inject lateinit var mOAuthLoginInstance: OAuthLogin

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignInBinding {
        val viewInflater = LayoutInflater.from(requireActivity())
        return FragmentSignInBinding.inflate(viewInflater, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        initNaverOAuth()
        loginWithKAKAO()
        setLoginButton()
        goToSignUp()
        loginZEEPY()
    }

    private fun setLoginButton() {
        binding.buttonLogin.apply {
            setText("로그인")
            onClick {
                viewModel.signIn()
            }
        }
    }

    private fun goToSignUp() {
        binding.textviewSignup.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun loginZEEPY() {
        viewModel.loginSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "사용자를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initNaverOAuth() {
        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(
            requireContext(),
            BuildConfig.NAVER_CLIENT_ID,
            BuildConfig.NAVER_CLIENT_SECRET_ID,
            "Zeepy"
        )
        binding.buttonNaverLogin.setOAuthLoginHandler(OAuthHandler())
    }

    private fun loginWithKAKAO() {
        binding.buttonLoginKakao.setOnClickListener {
            getKakaoLogin()
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
                    } else {
                        //토큰 유효성 체크 성공(필요 시 sdk내부에서 토큰 갱신됨)
                    }
                }
            } else {
                //단말에 토큰이 없으니 로그인 필요
                getKakaoLogin()
            }
        }
    }

    private fun getKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("error", "${error.message}")
                //Login Fail
            } else if (token != null) {
                viewModel.kakaoLogIn(token.accessToken)
            }
        }

        UserApiClient.instance.run {
            if (isKakaoTalkLoginAvailable(requireContext())) {
                loginWithKakaoTalk(requireContext(), callback = callback)
            } else {
                loginWithKakaoAccount(requireContext(), callback = callback)
            }
        }
    }

    inner class OAuthHandler: OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if(success) {
                val accessToken = mOAuthLoginInstance.getAccessToken(requireContext())
                viewModel.changeAccessToken(accessToken)
                viewModel.naverLogIn(accessToken)
            } else {

            }
        }
    }
}