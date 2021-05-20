package com.example.zeepyforandroid.community.postingdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.zeepyforandroid.community.data.entity.UrlPictureModel
import com.example.zeepyforandroid.databinding.ItemPictureBinding
import com.example.zeepyforandroid.util.DiffUtil

class PostingPictureAdapter: ListAdapter<UrlPictureModel, PostingPictureAdapter.PostingPictureViewHolder>(
    DiffUtil<UrlPictureModel>().diffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostingPictureViewHolder {
        val binding = ItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostingPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostingPictureViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
    }

    class PostingPictureViewHolder(val binding: ItemPictureBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: UrlPictureModel) {
            binding.picture.load(item.picture)
        }
    }
}