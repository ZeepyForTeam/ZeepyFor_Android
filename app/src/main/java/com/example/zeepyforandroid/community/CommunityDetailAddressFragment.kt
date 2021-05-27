package com.example.zeepyforandroid.community

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentCommunityDetailAddressBinding
import com.example.zeepyforandroid.util.CustomTypefaceSpan
import com.example.zeepyforandroid.util.ReviewNotice


class CommunityDetailAddressFragment : BaseFragment<FragmentCommunityDetailAddressBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCommunityDetailAddressBinding {
        return FragmentCommunityDetailAddressBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCommunityTheme()
    }

    private fun setCommunityTheme() {
        val typeface = Typeface.create(
            ResourcesCompat.getFont(requireContext(),R.font.nanum_square_round_extrabold),
            Typeface.NORMAL)
        val spannableString = binding.tvNotice.text.toSpannable()
        spannableString.setSpan(CustomTypefaceSpan(typeface), 0,4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        binding.apply {
            toolbar.apply {
                setTitle("커뮤니티")
            }
            layoutCommunityDetailAddress.btnNext.apply {
                setText("다음으로")
                setCommunityTheme()
            }
            tvNotice.text = spannableString
        }

    }
}
