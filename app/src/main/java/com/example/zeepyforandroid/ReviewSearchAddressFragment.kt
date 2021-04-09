package com.example.zeepyforandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.zeepyforandroid.databinding.FragmentReviewSearchAddressBinding


class ReviewSearchAddressFragment : Fragment() {
    private lateinit var binding: FragmentReviewSearchAddressBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewSearchAddressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar()
    }

    private fun setToolbar() {
        binding.reviewToolbar.setTitle("리뷰작성")
        binding.reviewToolbar.setBackButton {
            Toast.makeText(requireContext(), "success toolbar", Toast.LENGTH_SHORT).show()
        }
    }
}