package com.example.zeepyforandroid.community.storyzip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentStoryZipBinding


class StoryZipFragment : BaseFragment<FragmentStoryZipBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStoryZipBinding {
        return FragmentStoryZipBinding.inflate(inflater, container, false)
    }

}