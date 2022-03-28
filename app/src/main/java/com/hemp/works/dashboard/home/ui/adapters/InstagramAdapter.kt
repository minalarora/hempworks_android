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
import com.hemp.works.dashboard.home.ui.CategoryItemClickListener
import com.hemp.works.dashboard.home.ui.InstagramItemClickListener
import com.hemp.works.dashboard.model.Category
import com.hemp.works.dashboard.model.Instagram
import com.hemp.works.databinding.ItemCategoryBinding
import com.hemp.works.databinding.ItemInstagramBinding

class InstagramAdapter(private val listener: InstagramItemClickListener) : ListAdapter<Instagram, InstagramAdapter.ViewHolder>(InstagramDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemInstagramBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Instagram, listener: InstagramItemClickListener) {


            binding.model = item

            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemInstagramBinding.inflate(layoutInflater, parent, false)
                binding.root.clipToOutline = true
                return ViewHolder(binding)
            }
        }
    }
}


class InstagramDiffCallback: DiffUtil.ItemCallback<Instagram>() {

    override fun areItemsTheSame(oldItem: Instagram, newItem: Instagram): Boolean {
        return oldItem.post == newItem.post
    }


    override fun areContentsTheSame(oldItem: Instagram, newItem: Instagram): Boolean {
        return oldItem == newItem
    }


}