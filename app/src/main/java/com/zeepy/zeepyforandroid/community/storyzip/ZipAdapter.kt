package com.zeepy.zeepyforandroid.community.storyzip

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.community.data.entity.PostingDetailModel
import com.zeepy.zeepyforandroid.databinding.ItemStoryZipBinding
import com.zeepy.zeepyforandroid.util.DiffCallback

class ZipAdapter(val listener: (PostingDetailModel) -> Unit): ListAdapter<PostingDetailModel, ZipAdapter.StoryZipViewHolder>(
    DiffCallback<PostingDetailModel>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryZipViewHolder {
        val binding = ItemStoryZipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryZipViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoryZipViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        holder.binding.root.setOnClickListener{
            listener(item)
        }
    }

    class StoryZipViewHolder(val binding: ItemStoryZipBinding): RecyclerView.ViewHolder(binding.root)
}