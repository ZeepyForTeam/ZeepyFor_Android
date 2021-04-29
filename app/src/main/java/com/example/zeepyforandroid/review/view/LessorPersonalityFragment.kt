package com.example.zeepyforandroid.review.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.toSpannable
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
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

        val spannableText = binding.tvLessorCommunication.text.toSpannable()
        val typeface = Typeface.create(ResourcesCompat.getFont(requireContext(), R.font.nanum_square_round_extrabold), Typeface.NORMAL)
        spannableText.setSpan(CustomTypefaceSpan(typeface), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        spannableText.setSpan(CustomTypefaceSpan(typeface), 5, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)

        setToolbar()
        setNextButton()
        setLessorPersonalities()
    }

    private fun setToolbar() {
        binding.toolbar.run {
            setTitle("리뷰작성")
            setBackButton{
                Navigation.findNavController(binding.root).popBackStack()
            }
        }
    }

    private fun setNextButton() {
        binding.btnNext.run {
            setText("다음으로")
        }
    }

    private fun setLessorPersonalities() {
        binding.rvLessorCommunication.run {
            adapter = LessorPersonalityAdapter()
            addItemDecoration(ItemDecoration(10,0))
            (adapter as LessorPersonalityAdapter).submitList(viewModel.lessorPersonalities)
        }
    }
}