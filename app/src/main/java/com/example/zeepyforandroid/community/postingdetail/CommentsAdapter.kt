package com.example.zeepyforandroid.community.postingdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemCommentBinding
import com.example.zeepyforandroid.util.DiffCallback

class CommentsAdapter: ListAdapter<CommentModel,CommentsAdapter.CommentsViewHolder>(
    DiffCallback<CommentModel>()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)

        holder.binding.rvNestedComment.adapter = NestedCommentsAdapter()
    }

    class CommentsViewHolder(val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root)
}

