package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.MaterialSpinner
import com.zeepy.zeepyforandroid.databinding.FragmentLookaroundBinding
import com.zeepy.zeepyforandroid.lookaround.viewmodel.LookAroundViewModel
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LookAroundFragment : BaseFragment<FragmentLookaroundBinding>() {
    private val viewModel by viewModels<LookAroundViewModel>()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLookaroundBinding {
        return FragmentLookaroundBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("parentparent fragment is what?", requireParentFragment().parentFragment.toString())

        setToolbar()
        initRecyclerView()
        setSpinner()
        updateBuildings()
    }

    private val itemSelectedListener by lazy {
        object : MaterialSpinner.OnItemSelectedListener {
            override fun onItemSelected(
                parent: MaterialSpinner,
                view: View?,
                position: Int,
                id: Long
            ) {
                parent.focusSearch(View.FOCUS_UP)?.requestFocus()
                Log.e("view selected", view?.background.toString())
                Log.e("item selected", position.toString())
            }

            override fun onNothingSelected(parent: MaterialSpinner) {
                Log.e("MaterialSpinner", "onNothingSelected parent=${parent.id}")
            }
        }
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("둘러보기")
            setRightButton(R.drawable.btn_map) {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_mainFrameFragment_to_mapFragment)
            }
            setRightButton2(R.drawable.btn_filter) {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_mainFrameFragment_to_ConditionSearchFragment)
            }
            setRightButtonMargin(32)
            //TODO: 두번째 버튼도 사용가능한 ZeepyToolBar에서 margin세팅 모듈화
            val filterButton = binding.buttonRight2.layoutParams as ConstraintLayout.LayoutParams
            filterButton.marginEnd = 32
        }
    }

    private fun initRecyclerView() {
        binding.rvBuildingList.apply {
            adapter = LookAroundListAdapter {
                val action = MainFrameFragmentDirections.actionMainFrameFragmentToBuildingDetailFragment(
                    it
                )
                requireParentFragment().requireParentFragment().findNavController().navigate(action)
            }
            addItemDecoration(ItemDecoration(10, 0))
        }
    }

    private fun setSpinner() {
        ArrayAdapter.createFromResource(requireActivity(), R.array.lessor_personality_array, R.layout.item_lookaround_spinner).let {
            it.setDropDownViewResource(R.layout.item_lookaround_spinner_dropdown)
            binding.spinnerLessorPersonality.apply {
                adapter = it
                onItemSelectedListener = itemSelectedListener
                onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                    Log.v("MaterialSpinner", "onFocusChange hasFocus=$hasFocus")
                }
            }
        }
    }

    private fun updateBuildings() {
        viewModel.buildingList.observe(viewLifecycleOwner) {
            (binding.rvBuildingList.adapter as LookAroundListAdapter).submitList(it)
            // TODO: 주소가 등록되지 않았을 경우 OR 조회되는 주소가 하나도 없을 경우 뷰 처리 어떻게?
        }
    }


}