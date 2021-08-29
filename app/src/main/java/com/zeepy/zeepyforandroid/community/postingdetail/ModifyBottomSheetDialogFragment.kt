package com.zeepy.zeepyforandroid.community.postingdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.FragmentModifyBottomSheetDialogBinding

class ModifyBottomSheetDialogFragment(val listener: ModifyPostingListener):BottomSheetDialogFragment() {
    private var _binding: FragmentModifyBottomSheetDialogBinding? = null
    private val binding get() = _binding ?: error("error view binding")

    interface ModifyPostingListener {
        fun modify()
        fun delete()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentModifyBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textviewDelete.setOnClickListener {
            listener.delete()
            dismiss()
        }
        binding.textviewModify.setOnClickListener {
            listener.modify()
            dismiss()
        }
    }

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}