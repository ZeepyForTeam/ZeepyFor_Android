package com.zeepy.zeepyforandroid.myprofile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.base.BaseFragment
import com.zeepy.zeepyforandroid.customview.ZeepyToolbar
import com.zeepy.zeepyforandroid.databinding.FragmentWishlistBinding
import com.zeepy.zeepyforandroid.lookaround.adapter.LookAroundListAdapter
import com.zeepy.zeepyforandroid.mainframe.MainFrameFragmentDirections
import com.zeepy.zeepyforandroid.myprofile.adapter.MyReviewAdapter
import com.zeepy.zeepyforandroid.util.ItemDecoration

class WishListFragment: BaseFragment<FragmentWishlistBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWishlistBinding {
        return FragmentWishlistBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnBackPressed()
        setToolbar()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val uri = Uri.parse("myapp://buildingDetail")
        binding.rvWishlist.apply {
            adapter = LookAroundListAdapter(context) {
                findNavController().navigate(uri)
                requireParentFragment().requireParentFragment().view?.findViewById<ZeepyToolbar>(R.id.toolbar)
                    ?.visibility = View.GONE
            }
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