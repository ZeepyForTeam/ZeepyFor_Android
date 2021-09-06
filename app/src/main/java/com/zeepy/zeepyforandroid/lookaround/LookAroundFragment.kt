package com.zeepy.zeepyforandroid.lookaround

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.size
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.conditionsearch.data.ConditionSetModel
import com.zeepy.zeepyforandroid.databinding.FragmentLookaroundBinding
import com.zeepy.zeepyforandroid.lookaround.viewmodel.LookAroundViewModel
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.zeepy.zeepyforandroid.preferences.UserPreferenceManager
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LookAroundFragment : BaseFragment<FragmentLookaroundBinding>() {
    private val viewModel by viewModels<LookAroundViewModel>()
    @Inject lateinit var userPreferenceManager: UserPreferenceManager
    private lateinit var buildingsAdapter: LookAroundListAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLookaroundBinding {
        return FragmentLookaroundBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAddressListFromServer()
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

        Log.e("access token", "${userPreferenceManager.fetchUserAccessToken()}")

        subscribeObservers()
        setToolbar()
        setSwipeRefreshLayout()
        changeAddress()
        initRecyclerView()
        fetchPaginationBuildings()
        setFilteringListener()

        Log.e("list size", binding.rvBuildingList.size.toString())
    }

    private fun resetPostingList() {
        viewModel.removeBuildingsList()
        viewModel.changePaginationIdx(0)
    }

    private fun setSwipeRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            setOnRefreshListener {
                resetPostingList()
                viewModel.searchBuildingsByAddress()
            }
            viewModel.buildingListLiveData.observe(viewLifecycleOwner) {
                isRefreshing = false
            }
        }
    }

    private fun setFilteringListener() {
        binding.rgFilterings.setOnCheckedChangeListener { _, checkedId ->
            var lessorType = "BUSINESS"
            when (checkedId) {
                R.id.rb_standard_order -> {
                    if (viewModel.selectedAddress.value != null) {
                        // buildings order not preserved (refreshed from api call)
                        viewModel.searchBuildingsByAddress()
                    }
                }
                R.id.rb_business -> lessorType = "BUSINESS"
                R.id.rb_kind -> lessorType = "KIND"
                R.id.rb_graze -> lessorType = "GRAZE"
                R.id.rb_softy -> lessorType = "SOFTY"
                R.id.rb_bad -> lessorType = "BAD"
            }
            if (checkedId != R.id.rb_standard_order) {
                viewModel.setBuildingsByFiltering(lessorType)
            }
        }
    }

    private fun setToolbar() {
        binding.toolbar.apply {
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
                if (viewModel.addressList.value.isNullOrEmpty()) {
                    val action =
                        MainFrameFragmentDirections.actionMainFrameFragmentToReviewFrameFragment()
                    action.isCommunityTheme = false
                    action.isJustRegisterAddress = true
                    findNavController().navigate(action)
                } else {
                    val addresses = viewModel.addressList.value!!.toTypedArray()
                    val action =
                        MainFrameFragmentDirections.actionMainFrameFragmentToChangeAddressFragment(
                            addresses
                        )
                    requireParentFragment().requireParentFragment().findNavController()
                        .navigate(action)
                }
            } else {
                // 로그인 안된 상태 처리
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

                if (!binding.rvBuildingList.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    buildingsAdapter.deleteLoading()
                }


//                var loading = true
//
//                if (dy > 0) {
//                    val visibleItemCount = layoutManager.childCount
//                    val totalItemCount = layoutManager.itemCount
//                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
//
//                    if (loading) {
//                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
//                            loading = false
//                            if (viewModel.paginationIdx.value!! < viewModel.totalPages.value!!) {
//                                viewModel.increasePageIdx()
//                                Log.e("You have reached", "THE LAST ITEM!")
//                                viewModel.searchBuildingsByAddress()
//                                loading = true
//                            } else {
//                                Log.e("No more items to be loaded", "!!")
//                            }
//                        }
//                    }
//                }
            }
        })
    }

    private fun initRecyclerView() {
        binding.rvBuildingList.apply {
            buildingsAdapter = LookAroundListAdapter(context) {
                val action = MainFrameFragmentDirections.actionMainFrameFragmentToBuildingDetailFragment(
                    it
                )
                requireParentFragment().requireParentFragment().findNavController().navigate(action)
            }
            adapter = buildingsAdapter
            addItemDecoration(ItemDecoration(10, 0))
        }
    }

    private fun subscribeObservers() {
        viewModel.buildingListLiveData.observe(viewLifecycleOwner) {
            (binding.rvBuildingList.adapter as LookAroundListAdapter).submitList(it.toList())
        }
        viewModel.addressList.observe(viewLifecycleOwner) { addresses ->
            if (addresses.isNullOrEmpty()) {
                binding.toolbar.setTitle("주소 등록하기")
                binding.rvBuildingList.visibility = View.GONE
                binding.tvNoAddress.visibility = View.VISIBLE
            } else {
                binding.toolbar.setTitle(viewModel.selectedAddress.value?.cityDistinct!!)
                binding.tvNoAddress.visibility = View.GONE
                binding.rvBuildingList.visibility = View.VISIBLE
            }
        }
        viewModel.selectedAddress.observe(viewLifecycleOwner) {
            if (it != null) {
                Log.e("selectedAddress", " changed")
                viewModel.searchBuildingsByAddress()
            }
        }
    }

    /** Spinner 미사용 */
    /*
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

    private val itemSelectedListener by lazy {
        object : MaterialSpinner.OnItemSelectedListener {
            override fun onItemSelected(
                parent: MaterialSpinner,
                view: View?,
                position: Int,
                id: Long
            ) {
                parent.focusSearch(View.FOCUS_UP)?.requestFocus()

            }

            override fun onNothingSelected(parent: MaterialSpinner) {
                Log.e("MaterialSpinner", "onNothingSelected parent=${parent.id}")
            }
        }
    }*/
}