package com.zeepy.zeepyforandroid.myprofile

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.SpannedString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.color
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentMyProfileBinding
import com.zeepy.zeepyforandroid.myprofile.adapter.MyProfileOptionsAdapter
import com.zeepy.zeepyforandroid.util.CustomTypefaceSpan


class MyProfileFragment : BaseFragment<FragmentMyProfileBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMyProfileBinding {
        return FragmentMyProfileBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonsOnClickListener()
        setOptionsRecyclerView()
        changePartialText()
    }

    private fun setButtonsOnClickListener() {
        binding.ivManageAddress.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_ManageAddressFragment)
        }
        binding.ivManageReview.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_ManageReviewFragment)
        }
        binding.ivWishlist.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_wishListFragment)
        }
    }

    private fun setOptionsRecyclerView() {
        val options = arrayOf("환경설정", "문의 및 의견 보내기", "신고하기", "지피의 지기들", "현재 버전 1.1")

        binding.rvOptionsList.apply {
            setHasFixedSize(true)
            adapter = MyProfileOptionsAdapter(options)
        }
    }

    /**
     * Changes part of the text's font type and color using annotations in strings.xml
     * @see android.text.Annotation
     */
    private fun changePartialText() {
        val text = getText(R.string.myprofile_loggedout) as SpannedString
        val spannable = SpannableStringBuilder(text)
        val annotations = text.getSpans(0, text.length, android.text.Annotation::class.java)

        for (annotation in annotations) {
            if (annotation.key == "font") {
                val fontName = annotation.value
                val typeface = ResourcesCompat.getFont(requireContext(), context?.resources!!.getIdentifier(fontName, "font", context?.packageName))
                spannable.setSpan(CustomTypefaceSpan(typeface!!), spannable.getSpanStart(annotation), spannable.getSpanEnd(annotation), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            if (annotation.key == "color") {
                val colorName = annotation.value
                val colorId = context?.resources?.getIdentifier(colorName, "color", context?.packageName)
                spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), colorId!!)), spannable.getSpanStart(annotation), spannable.getSpanEnd(annotation), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        binding.tvMainMsg.text = spannable
    }
}