package com.zeepy.zeepyforandroid.lookaround

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.databinding.ItemLookaroundBuildingBinding
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel
import com.zeepy.zeepyforandroid.util.DiffCallback
import com.zeepy.zeepyforandroid.BR


class LookAroundListAdapter(val listener: (BuildingSummaryModel) -> Unit): RecyclerView.Adapter<LookAroundListAdapter.LookAroundListViewHolder>() {
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
        holder.binding.setVariable(BR.data, item)
        holder.binding.root.setOnClickListener {
            listener(item)
        }

        if (item.buildingType == "UNKNOWN" && item.reviews.isNullOrEmpty()) {
            // pass
        } else {
            if (item.reviews?.get(0)?.roomCount == "ONE") {
                holder.binding.tvOneroom.visibility = View.VISIBLE
            }
            if (item.reviews?.get(0)?.roomCount == "TWO") {
                holder.binding.tvOneroom.visibility = View.VISIBLE
            }
            if (item.reviews?.get(0)?.roomCount == "THREE") {
                holder.binding.tvOneroom.visibility = View.VISIBLE
            }
            if (item.buildingType != "UNKNOWN") {
                holder.binding.tvBuildingType.text = item.buildingType
                holder.binding.tvBuildingType.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    inner class LookAroundListViewHolder(val binding: ItemLookaroundBuildingBinding) : RecyclerView.ViewHolder(binding.root)
}