package com.zeepy.zeepyforandroid.community.postingdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.community.data.entity.CommentAuthenticatedModel
import com.zeepy.zeepyforandroid.community.data.entity.CommentModel
import com.zeepy.zeepyforandroid.databinding.ItemCommentBinding
import com.zeepy.zeepyforandroid.util.DiffCallback
import com.zeepy.zeepyforandroid.util.ItemDecoration

class CommentsAdapter(private val authenticatedUsers: CommentAuthenticatedModel, val listener: WriteNestedCommentListener) : ListAdapter<CommentModel, CommentsAdapter.CommentsViewHolder>(
        DiffCallback<CommentModel>()
) {
    interface WriteNestedCommentListener{
        fun write(item: CommentModel)
        fun report(item: CommentModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        holder.setSecretComment(item)
        setNestedComment(holder, item)
        writeNestedComment(holder, item)
    }

    private fun setNestedComment(holder: CommentsViewHolder, item: CommentModel) {
        holder.binding.rvNestedComment.apply {
            adapter = NestedCommentsAdapter(
                CommentAuthenticatedModel(
                    authenticatedUsers.currentUserIdx,
                    null,
                    item.commentWriterIdx
                )
            )
            addItemDecoration(ItemDecoration(10, 0))
            item.nestedComments?.let { (adapter as NestedCommentsAdapter).submitList(it) }
        }
    }

    private fun writeNestedComment(holder: CommentsViewHolder, item: CommentModel) {
        holder.binding.tvNestedComment.setOnClickListener {
            listener.write(item)
        }
        holder.binding.tvReport.setOnClickListener {
            listener.report(item)
        }
    }

    inner class CommentsViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setSecretComment(item: CommentModel) {
            authenticatedUsers.apply {
                if (item.isSecretComment) {
                    when (currentUserIdx) {
                        postingWriterIdx, item.commentWriterIdx -> binding.tvComment.text = item.comment
                        else -> binding.tvComment.text = "비밀 댓글입니다."
                    }
                } else {
                    binding.tvComment.text = item.comment
                }
            }
        }
    }
}

