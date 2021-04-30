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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentLessorPersonalityBinding
import com.example.zeepyforandroid.review.view.adapter.LessorPersonalityAdapter
import com.example.zeepyforandroid.review.viewmodel.WriteReviewViewModel
import com.example.zeepyforandroid.util.CustomTypefaceSpan
import com.example.zeepyforandroid.util.ItemDecoration
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LessorPersonalityFragment : BaseFragment<FragmentLessorPersonalityBinding>() {
    private val viewModel by activityViewModels<WriteReviewViewModel>()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLessorPersonalityBinding {
        return FragmentLessorPersonalityBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setNextButton()
        setLessorPersonalities()
        reviewNotice()
        goToWriteDetailLessorInfo()
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
            unUseableButton()
        }
    }

    private fun reviewNotice() {
        val parent = (parentFragment as NavHostFragment).parentFragment
        val notice = parent?.view?.findViewById<TextView>(R.id.tv_review_notice)
        notice?.text = getString(R.string.lessor_communication)
        val spannableText = notice?.text?.toSpannable()
        val typeface = Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.nanum_square_round_extrabold), Typeface.NORMAL)
        spannableText?.setSpan(CustomTypefaceSpan(typeface), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableText?.setSpan(CustomTypefaceSpan(typeface), 5, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }

    private fun setLessorPersonalities() {
        binding.rvLessorCommunication.run {
            adapter = LessorPersonalityAdapter{
                binding.btnNext.usableButton()
            }
            addItemDecoration(ItemDecoration(10,0))
            (adapter as LessorPersonalityAdapter).submitList(viewModel.lessorPersonalities)
        }
    }

    private fun goToWriteDetailLessorInfo() {
        binding.btnNext.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_lessorPersonalityFragment_to_writeLessorInfoFragment)
        }
    }
}