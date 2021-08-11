package com.zeepy.zeepyforandroid.review.view.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.databinding.ItemAddressBinding
import com.zeepy.zeepyforandroid.review.data.entity.AddressModel
import kotlin.properties.Delegates

class AddressAdapter(private val context: Context, val listener: ClickListener): ListAdapter<AddressModel, AddressAdapter.AddressViewHolder>(diffCallback) {

    interface ClickListener{
        fun delete(item: AddressModel)
        fun select(item: AddressModel)
    }

    private var selectedPosition by Delegates.observable(-1) { _, oldPos, newPos ->
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
            holder.binding.apply {
                root.setBackgroundResource(R.drawable.box_address_selected)
                tvAddress.typeface = changeFontFamily(R.font.nanum_square_round_extrabold)
            }
        } else {
            holder.binding.apply {
                root.setBackgroundResource(R.drawable.box_address)
                tvAddress.typeface = changeFontFamily(R.font.nanum_square_round_regular)
            }
        }
    }

    private fun changeFontFamily(font: Int):Typeface {
        return Typeface.create(ResourcesCompat.getFont(context, font), Typeface.NORMAL)
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