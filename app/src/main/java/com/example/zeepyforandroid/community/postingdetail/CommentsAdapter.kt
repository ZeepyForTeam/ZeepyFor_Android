package com.example.zeepyforandroid.community.postingdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.community.data.entity.CommentAuthenticatedModel
import com.example.zeepyforandroid.databinding.ItemCommentBinding
import com.example.zeepyforandroid.util.DiffCallback
import com.example.zeepyforandroid.util.ItemDecoration
import com.example.zeepyforandroid.util.SharedUtil
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class CommentsAdapter(private val authenticatedUsers: CommentAuthenticatedModel) : ListAdapter<CommentModel, CommentsAdapter.CommentsViewHolder>(
        DiffCallback<CommentModel>()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
        holder.setSecretComment(item)
        setNestedComment(holder, item)


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

            addItemDecoration(ItemDecoration(8, 0))
            if (item.nestedComments == null) {
                this.visibility = View.GONE
            } else {
                this.visibility = View.VISIBLE
                (adapter as NestedCommentsAdapter).submitList(item.nestedComments)
            }
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

