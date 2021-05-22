package com.example.zeepyforandroid.community.postingdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemCommentBinding
import com.example.zeepyforandroid.util.DiffCallback
import com.example.zeepyforandroid.util.ItemDecoration

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

        holder.binding.rvNestedComment.apply {
            adapter = NestedCommentsAdapter()
            addItemDecoration(ItemDecoration(8,0))
            if (item.nestedComments == null) {
                this.visibility = View.GONE
            } else {
                this.visibility = View.VISIBLE
                (adapter as NestedCommentsAdapter).submitList(item.nestedComments)
            }
        }
    }

    class CommentsViewHolder(val binding: ItemCommentBinding): RecyclerView.ViewHolder(binding.root)
}

