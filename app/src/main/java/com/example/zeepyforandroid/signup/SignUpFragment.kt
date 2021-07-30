package com.example.zeepyforandroid.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentSignUpBinding


class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    private val viewModel by viewModels<SignUpViewModel>()
    private val passwordEditTexts by lazy { listOf<EditText>(binding.edittextPassword, binding.edittextPasswordCheck) }

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
        checkPasswordCheckMatched()
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

    private fun checkPasswordCheckMatched() {
        passwordEditTexts.forEach { edittext ->
            edittext.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    checkPassword()
                }
            })
        }
    }

    private fun checkPassword() {
        if (passwordEditTexts[0].text.toString() == passwordEditTexts[1].text.toString()) {
            if(passwordEditTexts.all { !it.text.isNullOrEmpty() }) {
                viewModel.changeIsPasswordMatched(true)
            } else {
                viewModel.changeIsPasswordMatched(false)
            }
        } else {
            viewModel.changeIsPasswordMatched(false)
        }
    }

}