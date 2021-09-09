package com.zeepy.zeepyforandroid.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.databinding.ItemSearchAddressListBinding
import com.zeepy.zeepyforandroid.map.data.BuildingModel
import com.zeepy.zeepyforandroid.map.data.ResultSearchKeyword
import com.zeepy.zeepyforandroid.util.DiffCallback

class SearchBuildingAdapter(val listener: SelectBuildingInterface): ListAdapter<BuildingModel, SearchBuildingAdapter.SearchBuildingViewHolder>(
    DiffCallback<BuildingModel>()
) {
    interface SelectBuildingInterface {
        fun selectAddress(address: BuildingModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchBuildingViewHolder {
        val binding = ItemSearchAddressListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchBuildingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchBuildingViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.run {
            setVariable(BR.data, item)
            root.setOnClickListener {
                listener.selectAddress(item)
            }
        }
    }

    class SearchBuildingViewHolder(val binding: ItemSearchAddressListBinding): RecyclerView.ViewHolder(binding.root)
}