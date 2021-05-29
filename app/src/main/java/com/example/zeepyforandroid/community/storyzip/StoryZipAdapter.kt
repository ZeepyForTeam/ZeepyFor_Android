package com.example.zeepyforandroid.community.storyzip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.community.data.entity.PostingModel
import com.example.zeepyforandroid.databinding.ItemStoryZipBinding
import com.example.zeepyforandroid.util.DiffCallback

class StoryZipAdapter: ListAdapter<PostingModel, StoryZipAdapter.StoryZipViewHolder>(
    DiffCallback<PostingModel>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryZipViewHolder {
        val binding = ItemStoryZipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryZipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryZipViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
    }

    class StoryZipViewHolder(val binding: ItemStoryZipBinding): RecyclerView.ViewHolder(binding.root)
}