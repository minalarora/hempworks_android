package com.hemp.works.dashboard.home.ui.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hemp.works.R
import com.hemp.works.base.MyAppGlideModule
import com.hemp.works.dashboard.model.Banner
import com.hemp.works.databinding.ItemBannerImageBinding

class BannerAdapter : ListAdapter<Banner, BannerAdapter.ViewHolder>(BannerDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemBannerImageBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Banner) {
            Glide.with(binding.root.context)
                .load(item.url)
                .placeholder(ColorDrawable(ContextCompat.getColor(binding.root.context, R.color.white)))
                .error(ColorDrawable(ContextCompat.getColor(binding.root.context, R.color.white)))
                .apply(MyAppGlideModule.requestOptions)
                .into(binding.image);

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBannerImageBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


    class BannerDiffCallback : DiffUtil.ItemCallback<Banner>() {

        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.url == newItem.url
        }


        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }


}