package com.example.zeepyforandroid.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zeepyforandroid.BR
import com.example.zeepyforandroid.R
import com.example.zeepyforandroid.databinding.ItemHomeSelectFilterBinding

class HomeFilterAdapter: RecyclerView.Adapter<HomeFilterAdapter.HomeFilterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFilterViewHolder {
        val binding = ItemHomeSelectFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeFilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeFilterViewHolder, position: Int) {
        val item = filters[position]
        holder.binding.setVariable(BR.data, item)
    }

    override fun getItemCount() = filters.size

    companion object {
        private val filters = listOf<FilterImageEntity>(
            FilterImageEntity(
                R.string.lessor_business,
                R.drawable.ic_business_filter_home
            ),
            FilterImageEntity(
                R.string.lessor_kind,
                R.drawable.ic_kindness_filter_home
            ),
            FilterImageEntity(
                R.string.lessor_graze,
                R.drawable.ic_grazing_filter_home
            ),
            FilterImageEntity(
                R.string.lessor_softy,
                R.drawable.ic_softy_filter_home
            ),
            FilterImageEntity(
                R.string.lessor_bad,
                R.drawable.ic_worst_filter_home
            ),
            FilterImageEntity(
                R.string.filter_all_home,
                R.drawable.ic_all_filter_home
            )
        )
    }

    class HomeFilterViewHolder(val binding: ItemHomeSelectFilterBinding): RecyclerView.ViewHolder(binding.root)
}