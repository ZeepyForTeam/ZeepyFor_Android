package com.example.zeepyforandroid.community.postingdetail

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemNestedCommentBinding
import com.example.zeepyforandroid.util.DiffCallback

class NestedCommentsAdapter: ListAdapter<CommentModel, NestedCommentsAdapter.NestedCommentsViewHolder>(
    DiffCallback<CommentModel>()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedCommentsViewHolder {
        val binding = ItemNestedCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NestedCommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NestedCommentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
    }


    class NestedCommentsViewHolder(val binding: ItemNestedCommentBinding): RecyclerView.ViewHolder(binding.root)
}