package com.zeepy.zeepyforandroid.community.writeposting.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.BR
import com.zeepy.zeepyforandroid.community.data.entity.CollectContentModel
import com.zeepy.zeepyforandroid.databinding.ItemWritePostingCollectContentBinding

class CollectListAdapter(val listener: CollectListInterface): RecyclerView.Adapter<CollectListAdapter.CollectListViewHolder>() {
    interface CollectListInterface {
        fun select(item: CollectContentModel)
        fun unSelect(item: CollectContentModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectListViewHolder {
        val binding =
            ItemWritePostingCollectContentBinding.inflate(LayoutInflater.from(parent.context))
        return CollectListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CollectListViewHolder, position: Int) {
        val item = collectList[position]
        holder.binding.setVariable(BR.data, item)
        holder.binding.checkboxCollectContent.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                listener.select(item)
            } else {
                listener.unSelect(item)
            }
        }

    }

    override fun getItemCount() = collectList.size

    private val collectList = listOf<CollectContentModel>(
        CollectContentModel("제품명"),
        CollectContentModel("구매개수"),
        CollectContentModel("거래방식"),
        CollectContentModel("카카오톡 ID"),
        CollectContentModel("휴대폰 번호")
    )


    class CollectListViewHolder(val binding: ItemWritePostingCollectContentBinding) :
        RecyclerView.ViewHolder(binding.root)
}