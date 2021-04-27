package com.example.zeepyforandroid.review.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.navigation.Navigation
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteDetailAddressBinding
import com.example.zeepyforandroid.util.CustomTypefaceSpan


class WriteDetailAddressFragment : BaseFragment<FragmentWriteDetailAddressBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteDetailAddressBinding {
        return FragmentWriteDetailAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        val spannableText = binding.tvWriteAddress.text.toSpannable()
        val typeface = Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.nanum_square_round_extrabold), Typeface.NORMAL)
        spannableText.setSpan(CustomTypefaceSpan(typeface), 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        binding.toolbar.run {
            setTitle("리뷰작성")
            setBackButton{
                Navigation.findNavController(binding.root).popBackStack()
            }
        }

        binding.btnNext.run {
            setText("다음으로")
        }
    }
}