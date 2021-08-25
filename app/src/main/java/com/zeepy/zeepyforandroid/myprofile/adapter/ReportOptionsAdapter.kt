package com.zeepy.zeepyforandroid.myprofile.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.databinding.ItemMyprofileOptionBinding
import com.zeepy.zeepyforandroid.R

class ReportOptionsAdapter(private val dataSet: Array<String>):
    RecyclerView.Adapter<ReportOptionsAdapter.OptionsViewHolder>(
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val binding = ItemMyprofileOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.binding.tvTitle.text = dataSet[position]

        holder.itemView.setOnClickListener {

            Log.e("binding.root: ", "" + holder.binding.root)
            Log.e("itemView: ", "" + holder.itemView)
            Log.e("navController: ", "" + holder.itemView.findNavController())
            when (position) {
//                0 -> holder.itemView.findNavController().navigate(R.id.action_myProfileFragment_to_settingsFragment)
////                1 -> holder.itemView.findNavController().navigate(R.id.action_myProfileFragment_to_settingsFragment)
//                2 -> holder.itemView.findNavController().navigate(R.id.action_myProfileFragment_to_ziggysFragment)
////                3 -> holder.itemView.findNavController().navigate(R.id.action_myProfileFragment_to_settingsFragment)
            }
        }
    }


    class OptionsViewHolder(val binding: ItemMyprofileOptionBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = dataSet.size

}