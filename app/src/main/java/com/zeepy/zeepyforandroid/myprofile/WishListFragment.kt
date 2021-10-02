package com.zeepy.zeepyforandroid.myprofile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentWishlistBinding
import com.zeepy.zeepyforandroid.lookaround.adapter.LookAroundListAdapter
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.map.usecase.util.data
import com.zeepy.zeepyforandroid.myprofile.viewmodel.MyProfileViewModel
import com.zeepy.zeepyforandroid.util.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishListFragment: BaseFragment<FragmentWishlistBinding>() {
    private val viewModel by viewModels<MyProfileViewModel>()
    private lateinit var buildingsAdapter: LookAroundListAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWishlistBinding {
        return FragmentWishlistBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setOnBackPressed()
        setToolbar()
        initRecyclerView()

        viewModel.userWishList.observe(viewLifecycleOwner) {
            val prevLastItemPosition =
                if (buildingsAdapter.itemCount == 0) 0 else buildingsAdapter.itemCount
            it.getContentIfNotHandled()?.let { result ->
                buildingsAdapter.setListWithoutLoading(result.data as MutableList<BuildingSummaryModel>)
                buildingsAdapter.notifyItemRangeInserted(
                    prevLastItemPosition,
                    buildingsAdapter.itemCount - prevLastItemPosition
                )
            }
        }

        viewModel.getWishList()
    }

    private fun initRecyclerView() {
        binding.rvWishlist.apply {
            buildingsAdapter = LookAroundListAdapter(context) {
                val buildingObjectJson = Gson().toJson(it)
                val buildingObject: BuildingSummaryModel? = null
                findNavController().navigate(Uri.parse("myapp://buildingDetail/${buildingObjectJson}&amp;$buildingObject"))
                requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
                    ?.visibility = View.GONE
            }
            adapter = buildingsAdapter
            addItemDecoration(ItemDecoration(8, 0))
        }
    }

    private fun setToolbar() {
        requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
            ?.setTitle("찜 목록")
    }

    private fun setOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }
}