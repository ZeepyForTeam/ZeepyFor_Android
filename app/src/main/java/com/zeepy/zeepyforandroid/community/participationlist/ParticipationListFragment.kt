package com.zeepy.zeepyforandroid.community.participationlist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.databinding.FragmentParticipationListBinding

@Deprecated("use Zip Fragment")
class ParticipationListFragment : BaseFragment<FragmentParticipationListBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentParticipationListBinding {
        return FragmentParticipationListBinding.inflate(inflater, container, false)
    }
}