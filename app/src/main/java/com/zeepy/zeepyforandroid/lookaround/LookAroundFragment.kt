package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.conditionsearch.data.ConditionSetModel
import com.zeepy.zeepyforandroid.databinding.FragmentLookaroundBinding
import com.zeepy.zeepyforandroid.enum.CommunityTendency
import com.zeepy.zeepyforandroid.lookaround.adapter.LookAroundListAdapter
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.lookaround.viewmodel.LookAroundViewModel
import com.zeepy.zeepyforandroid.mainframe.MainActivity
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LookAroundFragment : BaseFragment<FragmentLookaroundBinding>() {
    private val viewModel by viewModels<LookAroundViewModel>()
    @Inject
    lateinit var userPreferenceManager: UserPreferenceManager
    private lateinit var buildingsAdapter: LookAroundListAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLookaroundBinding {
        return FragmentLookaroundBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        if (userPreferenceManager.fetchIsAlreadyLogin()) {
            viewModel.getAddressList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        // Observe for conditions set in ConditionSearchFragment
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<ConditionSetModel>("conditions")
            ?.observe(viewLifecycleOwner) {
                viewModel.setBuildingsByConditions(it)
            }

        binding.lifecycleOwner = viewLifecycleOwner


        setToolbar()
        initRecyclerView()
        subscribeObservers()
        setSwipeRefreshLayout()
        changeAddress()
        fetchPaginationBuildings()
        setFilteringListener()
        fetchBuildingsDirectFromHome((requireActivity() as MainActivity).initialLookAroundType)
    }

    private fun resetPostingList(buildingList: MutableLiveData<MutableList<BuildingSummaryModel>>) {
        buildingList.value?.clear()
        viewModel.changePaginationIdx(0)
        viewModel.resetIsLastPage()
        buildingsAdapter.clearList()
        buildingsAdapter.notifyDataSetChanged()
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            if (!userPreferenceManager.fetchIsAlreadyLogin()) {
                isEnabled = false
                isRefreshing = false
            } else {
                isEnabled = true
                setOnRefreshListener {
                    if (binding.rgFilterings.checkedRadioButtonId != R.id.rb_standard_order) {
                        binding.rgFilterings.check(R.id.rb_standard_order)
                    } else {
                        resetPostingList(viewModel.buildingListLiveData as MutableLiveData<MutableList<BuildingSummaryModel>>)
                        viewModel.searchBuildingsByAddress()
                    }
                    isRefreshing = false
                }
            }

        }
    }

    private fun changeTextColor(idx: Int) {
        (binding.rgFilterings[idx] as RadioButton).setTextColor((ContextCompat.getColor(requireContext(), R.color.zeepy_white_f0)))
        binding.rgFilterings.children.forEachIndexed { index, child ->
            if (index != idx) {
                (child as RadioButton).setTextColor((ContextCompat.getColor(requireContext(), R.color.zeepy_black_3b)))
            }
        }
    }

    private fun fetchBuildingsDirectFromHome(type: String?) {
        with(binding) {
            when (type) {
                CommunityTendency.BUSINESS.name -> rbBusiness.isChecked = true
                CommunityTendency.KIND.name -> rbKind.isChecked = true
                CommunityTendency.GRAZE.name -> rbGraze.isChecked = true
                CommunityTendency.SOFTY.name -> rbSofty.isChecked = true
                CommunityTendency.BAD.name -> rbBad.isChecked = true
                else -> rbStandardOrder.isChecked = true
            }
        }
    }

    private fun setFilteringListener() {
        binding.rgFilterings.setOnCheckedChangeListener { _, checkedId ->
            var lessorType = "BUSINESS"
            when (checkedId) {
                R.id.rb_standard_order -> {
                    changeTextColor(0)
                    if (viewModel.selectedAddress.value != null) {
                        viewModel.changeFilteredStatus(false)

                        // TODO: Modularize
                        viewModel.changePaginationIdx(0)
                        viewModel.resetIsLastPage()
                        buildingsAdapter.clearList()
                        buildingsAdapter.notifyDataSetChanged()
                        viewModel.buildingListLiveData.value?.let {
                            if (it.size != 0) {
                                buildingsAdapter.notifyItemRemoved(buildingsAdapter.itemCount)
                                val prevLastItemPosition =
                                    if (buildingsAdapter.itemCount == 0) 0 else buildingsAdapter.itemCount
                                if (viewModel.isLastPage.value != true) {
                                    buildingsAdapter.setList(it)
                                } else {
                                    buildingsAdapter.setListWithoutLoading(it)
                                }
                                buildingsAdapter.notifyItemRangeInserted(
                                    prevLastItemPosition,
                                    buildingsAdapter.itemCount - prevLastItemPosition
                                )
                            }
                        }
                    }
                }
                R.id.rb_business -> {
                    changeTextColor(1)
                    lessorType = "BUSINESS"
                }
                R.id.rb_kind -> {
                    changeTextColor(2)
                    lessorType = "KIND"
                }
                R.id.rb_graze -> {
                    changeTextColor(3)
                    lessorType = "GRAZE"
                }
                R.id.rb_softy -> {
                    changeTextColor(4)
                    lessorType = "SOFTY"
                }
                R.id.rb_bad -> {
                    changeTextColor(5)
                    lessorType = "BAD"
                }
            }
            if (checkedId != R.id.rb_standard_order) {
                viewModel.setFilterChecked(lessorType)
                viewModel.changeFilteredStatus(true)
                resetPostingList(viewModel.filteredBuildingList as MutableLiveData<MutableList<BuildingSummaryModel>>)

                viewModel.buildingListLiveData.observe(viewLifecycleOwner) {
                    if (!it.isNullOrEmpty() && it.size == viewModel.fetchedBuildingsCount.value) {
                        viewModel.setBuildingsByFiltering(lessorType)
                    }
                }
            }
        }
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            setTitle("주소 등록하기")
            setCommunityLocation()
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

    private fun changeAddress() {
        binding.toolbar.binding.textviewToolbar.setOnClickListener {
            if (userPreferenceManager.fetchIsAlreadyLogin()) {
                if (viewModel.addressList.value?.peekContent().isNullOrEmpty()) {
                    val action =
                        MainFrameFragmentDirections.actionMainFrameFragmentToReviewFrameFragment()
                    action.isCommunityTheme = false
                    action.isJustRegisterAddress = true
                    findNavController().navigate(action)
                } else {
                    val addresses = viewModel.addressList.value!!.peekContent().toTypedArray()
                    val action =
                        MainFrameFragmentDirections.actionMainFrameFragmentToChangeAddressFragment(
                            addresses
                        )
                    requireParentFragment().requireParentFragment().findNavController()
                        .navigate(action)
                }
            } else {
                // TODO: 로그인 안된 상태 처리
            }
        }
    }

    private fun fetchPaginationBuildings() {
        binding.rvBuildingList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1
                //Log.e("lastVisibleItemPosition", lastVisibleItemPosition.toString())
                //Log.e("itemTotalCount", itemTotalCount.toString())

                if (!binding.rvBuildingList.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount && viewModel.isLastPage.value == false
                    && itemTotalCount > 0) {
                    //Log.e("When is scroll not possible", "NOW")
                    buildingsAdapter.deleteLoading()
                    viewModel.buildingListLiveData.value?.clear()
                    viewModel.increasePageIdx()
                    viewModel.searchBuildingsByAddress()
                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvBuildingList.apply {
            buildingsAdapter = LookAroundListAdapter(context) {
                val action =
                    MainFrameFragmentDirections.actionMainFrameFragmentToBuildingDetailFragment(
                        it,
                        "fromLookAround"
                    )
                requireParentFragment().requireParentFragment().findNavController().navigate(action)
            }
            adapter = buildingsAdapter
            addItemDecoration(ItemDecoration(10, 0))
        }
    }

    private fun subscribeObservers() {
        viewModel.buildingListLiveData.observe(viewLifecycleOwner) {
            if (it.size == viewModel.fetchedBuildingsCount.value && viewModel.isOnFiltered.value == false) {
                if (it.size != 0) {
                    buildingsAdapter.notifyItemRemoved(buildingsAdapter.itemCount)
                    val prevLastItemPosition =
                        if (buildingsAdapter.itemCount == 0) 0 else buildingsAdapter.itemCount
                    if (viewModel.isLastPage.value != true) {
                        buildingsAdapter.setList(it)
                    } else {
                        buildingsAdapter.setListWithoutLoading(it)
                    }
                    buildingsAdapter.notifyItemRangeInserted(
                        prevLastItemPosition,
                        buildingsAdapter.itemCount - prevLastItemPosition
                    )
                }
            }
        }
        viewModel.filteredBuildingList.observe(viewLifecycleOwner) {
            if (it.size != 0) {
                val prevLastItemPosition =
                    if (buildingsAdapter.itemCount == 0) 0 else buildingsAdapter.itemCount
                // TODO: Paging 처리 for Filtered List
                buildingsAdapter.setListWithoutLoading(it)
                buildingsAdapter.notifyItemRangeInserted(
                    prevLastItemPosition,
                    buildingsAdapter.itemCount - prevLastItemPosition
                )
            }
        }
        viewModel.selectedAddress.observe(viewLifecycleOwner) {
            binding.tvNoAddress.visibility = View.GONE
            binding.rvBuildingList.visibility = View.VISIBLE
            binding.toolbar.apply {
                setTitle(it.cityDistinct)
            }
            if (viewModel.buildingListLiveData.value.isNullOrEmpty()) {
                viewModel.searchBuildingsByAddress()
            }
        }
        viewModel.addressList.observe(viewLifecycleOwner) { addresses ->
            addresses.getContentIfNotHandled()?.let {
                val selectedAddress = it.find { address -> address.isAddressCheck }
                if (selectedAddress != null) {
                    viewModel.changeSelectedAddress(selectedAddress)
                }
                if (it.isNullOrEmpty()) {
                    binding.tvNoAddress.visibility = View.VISIBLE
                    binding.rvBuildingList.visibility = View.GONE
                    binding.toolbar.apply {
                        setTitle("주소 등록하기")
                    }
                }
            }
        }
    }
}