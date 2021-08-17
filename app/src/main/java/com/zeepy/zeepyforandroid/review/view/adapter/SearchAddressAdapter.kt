package com.zeepy.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.databinding.ItemSearchAddressListBinding
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import com.zeepy.zeepyforandroid.util.DiffCallback

class SearchAddressAdapter: ListAdapter<SearchAddressListModel, SearchAddressAdapter.SearchAddressViewHolder>(
    DiffCallback<SearchAddressListModel>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAddressViewHolder {
        val binding = ItemSearchAddressListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchAddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAddressViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
    }

    class SearchAddressViewHolder(val binding: ItemSearchAddressListBinding): RecyclerView.ViewHolder(binding.root)
}