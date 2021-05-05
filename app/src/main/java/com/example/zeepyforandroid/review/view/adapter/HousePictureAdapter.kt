package com.example.zeepyforandroid.review.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemReviewPictureBinding
import com.example.zeepyforandroid.review.data.dto.HousePictureModel

class HousePictureAdapter: ListAdapter<HousePictureModel, HousePictureAdapter.HousePictureViewHolder>(
    diffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HousePictureViewHolder {
        val binding =  ItemReviewPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HousePictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HousePictureViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        Log.d("current list", currentList.toString())
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<HousePictureModel>(){
            override fun areItemsTheSame(
                oldItem: HousePictureModel,
                newItem: HousePictureModel
            ): Boolean {
                return oldItem.image == newItem.image
            }

            override fun areContentsTheSame(
                oldItem: HousePictureModel,
                newItem: HousePictureModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    class HousePictureViewHolder(val binding: ItemReviewPictureBinding): RecyclerView.ViewHolder(binding.root)
}