package com.zeepy.zeepyforandroid.lookaround

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.databinding.ItemLookaroundBuildingBinding
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.util.DiffCallback
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.databinding.ItemLoadingBinding
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingFeatureModel
import com.zeepy.zeepyforandroid.util.ItemDecoration


class LookAroundListAdapter(val context: Context, val listener: (BuildingSummaryModel) -> Unit): ListAdapter<BuildingSummaryModel, RecyclerView.ViewHolder>(
    DiffCallback<BuildingSummaryModel>()
) {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val items = ArrayList<BuildingSummaryModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LookAroundListViewHolder {
        val binding = ItemLookaroundBuildingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LookAroundListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        val features = arrayListOf<BuildingFeatureModel>()

        if (holder is LookAroundListViewHolder) {
            holder.binding.rvBuildingFeatures.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
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
        } else {
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position].buildingName) {
            " " -> VIEW_TYPE_LOADING
            else -> VIEW_TYPE_ITEM
        }
    }

    fun setList(buildings: MutableList<BuildingSummaryModel>) {
        items.addAll(buildings)
        items.add(BuildingSummaryModel(-1, " ", " ", " ", " ", " ", " ", -1, -1, -1.0, -1.0, -1.0, " "))
    }

    fun deleteLoading() {
        items.removeAt(items.lastIndex)
    }

    inner class LookAroundListViewHolder(val binding: ItemLookaroundBuildingBinding) : RecyclerView.ViewHolder(binding.root)

    inner class LoadingViewHolder(val binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)
}