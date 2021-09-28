package com.zeepy.zeepyforandroid.myprofile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.databinding.ItemMyprofileOptionBinding
import com.zeepy.zeepyforandroid.R

class MyProfileOptionsAdapter(private val dataSet: Array<String>, val listener: (Int) -> Unit):
    RecyclerView.Adapter<MyProfileOptionsAdapter.OptionsViewHolder>(
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val binding = ItemMyprofileOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.binding.tvTitle.text = dataSet[position]

        holder.itemView.setOnClickListener {
            listener(position)
        }
    }


    class OptionsViewHolder(val binding: ItemMyprofileOptionBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = dataSet.size

}