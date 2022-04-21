package com.hemp.works.dashboard.home.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.home.ui.BlogItemClickListener
import com.hemp.works.dashboard.model.Blog
import com.hemp.works.dashboard.model.Product
import com.hemp.works.dashboard.product.ui.ProductItemClickListener
import com.hemp.works.databinding.ItemBlogBinding
import com.hemp.works.databinding.ItemProductBinding

class BlogAdapter(private val listener: BlogItemClickListener) : ListAdapter<Blog, BlogAdapter.ViewHolder>(BlogDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemBlogBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Blog, listener: BlogItemClickListener) {
            binding.viewmodel = BlogViewModel(item)
            binding.root.setOnClickListener {
                listener.onItemClick(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBlogBinding.inflate(layoutInflater, parent, false)
                binding.root.clipToOutline = true
                return ViewHolder(binding)
            }
        }
    }
}


class BlogDiffCallback: DiffUtil.ItemCallback<Blog>() {

    override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem.image == newItem.image
    }


    override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
        return oldItem == newItem
    }


}