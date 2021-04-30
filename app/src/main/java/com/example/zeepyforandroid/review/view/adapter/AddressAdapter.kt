package com.example.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.ItemAddressBinding
import com.example.zeepyforandroid.review.data.dto.AddressModel
import kotlin.properties.Delegates

class AddressAdapter(val listener: ClickListener): ListAdapter<AddressModel, AddressAdapter.AddressViewHolder>(diffCallback) {

    interface ClickListener{
        fun delete(item: AddressModel)
        fun select(item: AddressModel)
    }

    var selectedPosition by Delegates.observable(-1) { _, oldPos, newPos ->
        if (newPos in currentList.indices){
            notifyItemChanged(oldPos)
            notifyItemChanged(newPos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        holder.binding.btnDelete.setOnClickListener {
            listener.delete(item)
            notifyItemRemoved(position)
        }
        holder.binding.root.setOnClickListener {
            listener.select(item)
            selectedPosition = position
        }

        if (position == selectedPosition){
            holder.binding.root.setBackgroundResource(R.drawable.box_address_selected)
        } else {
            holder.binding.root.setBackgroundResource(R.drawable.box_address)
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