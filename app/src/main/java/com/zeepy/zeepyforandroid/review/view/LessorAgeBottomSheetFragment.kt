package com.zeepy.zeepyforandroid.review.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.FragmentLessorAgeBottomSheetBinding
import com.zeepy.zeepyforandroid.enum.LessorAge.Companion.findLessorAge
import com.zeepy.zeepyforandroid.review.AgeSelectListener

class LessorAgeBottomSheetFragment(private val listener: AgeSelectListener): BottomSheetDialogFragment() {
    private lateinit var binding: FragmentLessorAgeBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLessorAgeBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectAge()
    }

    private fun selectAge() {
        val ageTextViewList = listOf(
            binding.textviewTen,
            binding.textviewTwenty,
            binding.textviewThirty,
            binding.textviewFourty,
            binding.textviewFifty,
            binding.textviewSixty,
            binding.textviewUnknown)

        ageTextViewList.forEach { textview ->
            textview.setOnClickListener {
                listener.selectAge(findLessorAge(textview.text.toString()))
                dismiss()
            }
        }
    }

    override fun getTheme() = R.style.BottomSheetDialogTheme
}