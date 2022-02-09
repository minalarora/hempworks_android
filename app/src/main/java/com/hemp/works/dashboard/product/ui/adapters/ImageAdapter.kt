package com.hemp.works.dashboard.product.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.product.ui.ProductImageClickListener
import com.hemp.works.databinding.ItemProductImageBinding

class ImageAdapter(private val listener: ProductImageClickListener) : ListAdapter<String, ImageAdapter.ViewHolder>(ImageDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemProductImageBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(url: String, listener: ProductImageClickListener) {
            binding.url = url
            binding.root.setOnClickListener { listener.onProductImage(url) }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductImageBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class ImageDiffCallback : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }


    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }


}