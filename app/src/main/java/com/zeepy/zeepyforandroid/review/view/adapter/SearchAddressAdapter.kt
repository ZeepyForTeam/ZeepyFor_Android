package com.zeepy.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.databinding.ItemSearchAddressListBinding
import com.zeepy.zeepyforandroid.review.data.entity.SearchAddressListModel
import com.zeepy.zeepyforandroid.util.DiffCallback

class SearchAddressAdapter(val listener: SelectAddressInterface): ListAdapter<SearchAddressListModel, SearchAddressAdapter.SearchAddressViewHolder>(
    DiffCallback<SearchAddressListModel>()
) {
    interface SelectAddressInterface {
        fun selectAddress(address: SearchAddressListModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAddressViewHolder {
        val binding = ItemSearchAddressListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchAddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAddressViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.run {
            setVariable(BR.data, item)
            root.setOnClickListener {
                listener.selectAddress(item)
            }
        }
    }

    class SearchAddressViewHolder(val binding: ItemSearchAddressListBinding): RecyclerView.ViewHolder(binding.root)
}