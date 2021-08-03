package com.example.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toFile
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.zeepyforandroid.databinding.ItemPictureBinding
import com.example.zeepyforandroid.review.data.entity.PictureModel
import com.example.zeepyforandroid.util.DiffUtil

class HousePictureAdapter: ListAdapter<PictureModel, HousePictureAdapter.HousePictureViewHolder>(
    DiffUtil<PictureModel>().diffCallback()
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