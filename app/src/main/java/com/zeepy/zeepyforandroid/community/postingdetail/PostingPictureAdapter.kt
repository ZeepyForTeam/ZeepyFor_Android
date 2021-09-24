package com.zeepy.zeepyforandroid.community.postingdetail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.zeepy.zeepyforandroid.community.data.entity.UrlPictureModel
import com.zeepy.zeepyforandroid.databinding.ItemPictureBinding
import com.zeepy.zeepyforandroid.util.DiffCallback

class PostingPictureAdapter: ListAdapter<UrlPictureModel, PostingPictureAdapter.PostingPictureViewHolder>(
    diffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostingPictureViewHolder {
        val binding = ItemPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostingPictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostingPictureViewHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(item)
        Log.e("imagesssss???", "${item.picture}")
    }

    class PostingPictureViewHolder(val binding: ItemPictureBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: UrlPictureModel) {
            binding.picture.load(item.picture)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<UrlPictureModel>() {
            override fun areItemsTheSame(oldItem: UrlPictureModel, newItem: UrlPictureModel): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: UrlPictureModel,
                newItem: UrlPictureModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}