package com.zeepy.zeepyforandroid.myprofile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.zeepy.zeepyforandroid.databinding.ItemMyprofileOptionBinding
import com.zeepy.zeepyforandroid.R
import com.zeepy.zeepyforandroid.myprofile.reportShowConfirmDialog

class ReportOptionsAdapter(private val dataSet: Array<String>, private val myFragment: Fragment):
    RecyclerView.Adapter<ReportOptionsAdapter.OptionsViewHolder>(
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val binding = ItemMyprofileOptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        holder.binding.tvTitle.text = dataSet[position]

        holder.itemView.setOnClickListener {

            when (position) {
                0, 1, 2, 3 -> reportShowConfirmDialog(myFragment)
                4 -> holder.itemView.findNavController().navigate(R.id.action_reportFragment3_to_reportOtherFragment3)
            }
        }
    }


    class OptionsViewHolder(val binding: ItemMyprofileOptionBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount() = dataSet.size

}