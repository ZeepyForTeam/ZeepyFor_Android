package com.zeepy.zeepyforandroid.util

import androidx.recyclerview.widget.DiffUtil
import com.zeepy.zeepyforandroid.lookaround.data.entity.BuildingSummaryModel

class BuildingDiffCallback(
    private val oldBuildings: List<BuildingSummaryModel>,
    private val newBuildings: List<BuildingSummaryModel>
    ) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldBuildings.size
    }

    override fun getNewListSize(): Int {
        return newBuildings.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldBuildings[oldItemPosition].id == newBuildings[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldBuildings[oldItemPosition] == newBuildings[newItemPosition]
    }
}