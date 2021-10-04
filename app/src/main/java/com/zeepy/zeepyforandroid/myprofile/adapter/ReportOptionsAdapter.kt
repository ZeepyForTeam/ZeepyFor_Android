package com.zeepy.zeepyforandroid.myprofile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.databinding.ItemMyprofileOptionBinding

class ReportOptionsAdapter(private val dataSet: Array<String>, private val listener: (Int) -> Unit):
    RecyclerView.Adapter<ReportOptionsAdapter.OptionsViewHolder>(
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val binding = ItemMyprofileOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.binding.tvTitle.text = dataSet[position]

        holder.binding.root.setOnClickListener {
            listener(position)
        }
    }


    class OptionsViewHolder(val binding: ItemMyprofileOptionBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = dataSet.size

}