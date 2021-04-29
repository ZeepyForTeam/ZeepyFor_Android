package com.example.zeepyforandroid.review.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.navigation.fragment.NavHostFragment
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentWriteLessorInfoBinding
import com.example.zeepyforandroid.util.CustomTypefaceSpan

class WriteLessorInfoFragment : BaseFragment<FragmentWriteLessorInfoBinding>() {

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWriteLessorInfoBinding {
        return FragmentWriteLessorInfoBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNextButton()
        setReviewNotice()
    }
    private fun setReviewNotice() {
        val parent = (parentFragment as NavHostFragment).parentFragment
        val notice = parent?.view?.findViewById<TextView>(R.id.tv_review_notice)
        notice?.text = getString(R.string.lessor_detail_info)
        val spannableText = notice?.text?.toSpannable()
        val typeface = Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.nanum_square_round_extrabold), Typeface.NORMAL)
        spannableText?.setSpan(CustomTypefaceSpan(typeface), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableText?.setSpan(CustomTypefaceSpan(typeface), 13, 19, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
        }
    }
}