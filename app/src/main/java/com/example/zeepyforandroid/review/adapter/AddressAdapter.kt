package com.example.zeepyforandroid.review.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemAddressBinding
import com.example.zeepyforandroid.review.dto.AddressModel

class AddressAdapter(val listener: (AddressModel) -> Unit): ListAdapter<AddressModel, AddressAdapter.AddressViewHolder>(diffCallback) {

    interface DeleteAddress{
        fun delete(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        holder.binding.btnDelete.setOnClickListener {
            listener(item)
            notifyItemRemoved(position)
        }
    }

    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<AddressModel>(){
            override fun areItemsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: AddressModel, newItem: AddressModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class AddressViewHolder(val binding : ItemAddressBinding): RecyclerView.ViewHolder(binding.root)
}