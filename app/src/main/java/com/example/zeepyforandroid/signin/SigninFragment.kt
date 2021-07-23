package com.example.zeepyforandroid.signin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.zeepyforandroid.BuildConfig
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentSigninBinding
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler


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
    }

    private fun initNaverOAuth() {
        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(requireContext(), BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_SECRET_ID, "Zeepy")
        val mOAuthHandler = OAuthHandler()
        binding.buttonNaverLogin.setOAuthLoginHandler(mOAuthHandler)
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