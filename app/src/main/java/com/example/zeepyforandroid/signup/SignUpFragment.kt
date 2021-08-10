package com.example.zeepyforandroid.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentSignUpBinding
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

    private fun setInitView() {
        binding.toolbar.apply {
            setTitle("회원가입")
            setBackButton{

            }
        }
        binding.buttonSignup.apply {
            setText("완료")
            onClick{

            }
        }
    }
}