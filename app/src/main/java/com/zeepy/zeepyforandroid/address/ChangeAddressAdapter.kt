package com.zeepy.zeepyforandroid.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.databinding.ItemChangeAddressBinding
import com.zeepy.zeepyforandroid.util.DiffCallback

class ChangeAddressAdapter(val listener: (LocalAddressEntity)-> Unit): ListAdapter<LocalAddressEntity, ChangeAddressAdapter.ChangeAddressViewHolder>(
    DiffCallback<LocalAddressEntity>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangeAddressViewHolder {
        val binding = ItemChangeAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChangeAddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChangeAddressViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        holder.binding.root.setOnClickListener{
            listener(item)
        }
    }

    class ChangeAddressViewHolder(val binding: ItemChangeAddressBinding): RecyclerView.ViewHolder(binding.root)
}