package com.example.zeepyforandroid.review.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.databinding.ItemLessorPersonalityBinding
import com.example.zeepyforandroid.review.dto.LessorPersonalityModel

class LessorPersonalityAdapter: ListAdapter<LessorPersonalityModel, LessorPersonalityAdapter.LessorPersonalityViewHolder>(
    diffCallback
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessorPersonalityViewHolder {
        val binding = ItemLessorPersonalityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessorPersonalityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessorPersonalityViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.setVariable(BR.data, item)
    }


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<LessorPersonalityModel>(){
            override fun areItemsTheSame(
                oldItem: LessorPersonalityModel,
                newItem: LessorPersonalityModel
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(
                oldItem: LessorPersonalityModel,
                newItem: LessorPersonalityModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
    class LessorPersonalityViewHolder(val binding: ItemLessorPersonalityBinding): RecyclerView.ViewHolder(binding.root)
}