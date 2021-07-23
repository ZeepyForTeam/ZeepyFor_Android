package com.example.zeepyforandroid.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.community.data.entity.CollectContentModel
import com.example.zeepyforandroid.databinding.ItemWritePostingCollectContentBinding

class CollectListAdapter: RecyclerView.Adapter<CollectListAdapter.CollectListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectListViewHolder {
        val binding = ItemWritePostingCollectContentBinding.inflate(LayoutInflater.from(parent.context))
        return CollectListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectListViewHolder, position: Int) {
        val item = collectList[position]
        holder.binding.setVariable(BR.data, item)
    }

    override fun getItemCount() = collectList.size

    companion object {
        private val collectList = listOf<CollectContentModel>(
            CollectContentModel(
                "제품명"
            ),
            CollectContentModel(
                "구매개수"
            ),
            CollectContentModel(
                "거래방식"
            ),
            CollectContentModel(
                "카카오톡 ID"
            ),
            CollectContentModel(
                "휴대폰 번호"
            )
        )
    }

    class CollectListViewHolder(val binding: ItemWritePostingCollectContentBinding): RecyclerView.ViewHolder(binding.root)
}