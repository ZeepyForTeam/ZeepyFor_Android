package com.example.zeepyforandroid.community.participationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zeepyforandroid.base.BaseFragment
import com.example.zeepyforandroid.databinding.FragmentParticipationListBinding

class ParticipationListFragment : BaseFragment<FragmentParticipationListBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentParticipationListBinding {
        return FragmentParticipationListBinding.inflate(inflater, container, false)
    }
}