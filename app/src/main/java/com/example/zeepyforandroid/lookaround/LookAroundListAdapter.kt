package com.example.zeepyforandroid.lookaround

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemLookaroundBuildingBinding
import com.example.zeepyforandroid.lookaround.data.entity.LookAroundBuildingSummaryModel
import com.example.zeepyforandroid.util.DiffCallback

class LookAroundListAdapter: RecyclerView.Adapter<LookAroundListAdapter.LookAroundListViewHolder>() {
    private val diffcallback = DiffCallback<LookAroundBuildingSummaryModel>()
    private val differ = AsyncListDiffer(this, diffcallback)

    fun submitList(list: List<LookAroundBuildingSummaryModel>?) = differ.submitList(list)

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
    }

    override fun getItemCount() = differ.currentList.size

    inner class LookAroundListViewHolder(val binding: ItemLookaroundBuildingBinding) : RecyclerView.ViewHolder(binding.root)
}