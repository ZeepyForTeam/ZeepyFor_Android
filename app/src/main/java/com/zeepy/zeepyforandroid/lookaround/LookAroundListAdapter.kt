package com.zeepy.zeepyforandroid.lookaround

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.databinding.ItemLookaroundBuildingBinding
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.util.DiffCallback
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingFeatureModel
import com.zeepy.zeepyforandroid.util.ItemDecoration


class LookAroundListAdapter(val context: Context, val listener: (BuildingSummaryModel) -> Unit): RecyclerView.Adapter<LookAroundListAdapter.LookAroundListViewHolder>() {
    private val diffCallback = DiffCallback<BuildingSummaryModel>()
    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<BuildingSummaryModel>?) = differ.submitList(list)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LookAroundListViewHolder {
        val binding = ItemLookaroundBuildingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LookAroundListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LookAroundListViewHolder, position: Int) {
        val item = differ.currentList[position]
        val features = arrayListOf<BuildingFeatureModel>()

        holder.binding.rvBuildingFeatures.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            //(this.adapter as BuildingFeaturesAdapter).submitList(features)
            //addItemDecoration(ItemDecoration(0, 7))
        }

        holder.binding.setVariable(BR.data, item)
        holder.binding.root.setOnClickListener {
            listener(item)
        }

        if (item.buildingType == "UNKNOWN" && item.reviews.isNullOrEmpty()) {
            // pass
        } else if (!item.reviews.isNullOrEmpty()) {
            features.add(BuildingFeatureModel(item.reviews[0].roomCount, "RoomCount"))
            features.add(BuildingFeatureModel(item.buildingType, "BuildingType"))

            holder.binding.tvWaitingUpdate.visibility = View.GONE
            holder.binding.rvBuildingFeatures.visibility = View.VISIBLE

        } else {
            holder.binding.tvWaitingUpdate.visibility = View.VISIBLE
            holder.binding.rvBuildingFeatures.visibility = View.GONE
        }

        holder.binding.rvBuildingFeatures.adapter = BuildingFeaturesAdapter(context, features)
    }

    override fun getItemCount() = differ.currentList.size

    inner class LookAroundListViewHolder(val binding: ItemLookaroundBuildingBinding) : RecyclerView.ViewHolder(binding.root)
}