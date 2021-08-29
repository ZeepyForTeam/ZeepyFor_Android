package com.zeepy.zeepyforandroid.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentSignUpBinding
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    private val viewModel by viewModels<SignUpViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSignUpBinding {
        return FragmentSignUpBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setInitView()
        showEmailRepetitionNotice()
        showNickNameRepetitionNotice()
        successSignUp()
        checkEveryInputEntered()
        checkRepetition()
        showTermsInfo()
    }

    private fun setInitView() {
        binding.toolbar.apply {
            setTitle("회원가입")
            setBackButton{

            }
        }

        binding.buttonSignup.apply {
            setText("완료")
            setUnUsableButton()
            onClick{
                viewModel.signUp()
            }
        }
    }

    private fun checkEveryInputEntered() {
        val inputList = listOf(viewModel.email, viewModel.name, viewModel.nickname, viewModel.isEmailRepetition, viewModel.isNickNameRepetition,
         viewModel.password, viewModel.passwordCheck,viewModel.termsApprove, viewModel.personalInfoApprove)

        inputList.forEach { liveData ->
            liveData.observe(viewLifecycleOwner) {
                Log.e("${liveData.value}", "$it")
                viewModel.checkPasswordCheckMatched()
                viewModel.checkEveryInputEntered()
            }
        }

        viewModel.isInputEverythig.observe(viewLifecycleOwner) { isEnabledSignUp ->
            Log.e("isEnabledSignUp", "$isEnabledSignUp")
            if (isEnabledSignUp) {
                binding.buttonSignup.setUsableButton()
            } else {
                binding.buttonSignup.setUnUsableButton()
            }
        }
    }

    private fun checkRepetition() {
        viewModel.nickname.observe(viewLifecycleOwner) {
            viewModel.changeTrueNickNameRepetition()
        }
        viewModel.email.observe(viewLifecycleOwner) {
            viewModel.changeTrueEmailRepetition()
        }
    }

    private fun showEmailRepetitionNotice() {
        viewModel.isEmailRepetition.observe(viewLifecycleOwner) { isRepetition ->
            binding.textviewEmailRepetition.run {
                visibility = if (isRepetition) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }

    private fun showNickNameRepetitionNotice() {
        viewModel.isNickNameRepetition.observe(viewLifecycleOwner) { isRepetition ->
            binding.textviewNicknameRepetition.run {
                visibility = if (isRepetition) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }
        }
    }

    private fun successSignUp() {
        viewModel.signUpSuccess.observe(viewLifecycleOwner) {
            if(it) {
                findNavController().popBackStack()
            }
        }
    }

    private fun showTermsInfo() {
        binding.textviewShowTerms.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToTermsWebviewFragment(
                TermsWebviewFragment.Companion.WebViewType.ZEEPY_TERMS.name
            )
            findNavController().navigate(action)

        }
        binding.textviewShowTermsPersonalInfo.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToTermsWebviewFragment(
                TermsWebviewFragment.Companion.WebViewType.PERSONAL_INFO.name
            )
            findNavController().navigate(action)
        }
    }
}