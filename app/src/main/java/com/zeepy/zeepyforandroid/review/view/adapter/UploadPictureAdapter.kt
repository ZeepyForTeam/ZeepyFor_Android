package com.zeepy.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zeepy.zeepyforandroid.databinding.ItemPictureBinding
import com.zeepy.zeepyforandroid.review.data.entity.PictureModel
import com.zeepy.zeepyforandroid.util.DiffCallback

class UploadPictureAdapter: ListAdapter<PictureModel, UploadPictureAdapter.HousePictureViewHolder>(
    DiffCallback<PictureModel>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HousePictureViewHolder {
        val binding =  ItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HousePictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HousePictureViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    class HousePictureViewHolder(val binding: ItemPictureBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: PictureModel) {
            binding.picture.load(item.image)
        }
    }
}