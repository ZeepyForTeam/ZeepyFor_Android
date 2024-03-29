package com.zeepy.zeepyforandroid.community.postingdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.community.data.entity.CommentAuthenticatedModel
import com.zeepy.zeepyforandroid.community.data.entity.NestedCommentModel
import com.zeepy.zeepyforandroid.databinding.ItemNestedCommentBinding
import com.zeepy.zeepyforandroid.util.DiffCallback

class NestedCommentsAdapter(private val authenticatedUsers: CommentAuthenticatedModel): ListAdapter<NestedCommentModel, NestedCommentsAdapter.NestedCommentsViewHolder>(
    DiffCallback<NestedCommentModel>()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedCommentsViewHolder {
        val binding = ItemNestedCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NestedCommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NestedCommentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.run {
            binding.setVariable(BR.data, item)
            setSecretComment(item)
        }
    }

    inner class NestedCommentsViewHolder(val binding: ItemNestedCommentBinding): RecyclerView.ViewHolder(binding.root) {
        fun setSecretComment(item: NestedCommentModel) {
            authenticatedUsers.apply {
                if (item.isSecretComment == true) {
                    when(currentUserIdx) {
                        item.writerUserIdx, this.commentWriterIdx -> binding.tvNestedComment.text = item.comment
                        else -> binding.tvNestedComment.text = "비밀 댓글입니다."
                    }
                } else {
                    binding.tvNestedComment.text = item.comment
                }
            }
        }
    }
}