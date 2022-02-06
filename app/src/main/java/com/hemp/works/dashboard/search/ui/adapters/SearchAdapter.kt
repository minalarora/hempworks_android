package com.hemp.works.dashboard.search.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.R
import com.hemp.works.dashboard.model.Product
import com.hemp.works.dashboard.search.ui.SearchItemClickListener
import com.hemp.works.databinding.ItemSearchBinding

class SearchAdapter(private val listener: SearchItemClickListener) : ListAdapter<Product, SearchAdapter.ViewHolder>(SearchDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Product, listener: SearchItemClickListener) {
            binding.title.text = item.title
            binding.subTitle.text = binding.root.context.getString(R.string.product_category_in, item.categoryname)
            binding.root.setOnClickListener {
                listener.onItemClick(item.id, item.category)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSearchBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class SearchDiffCallback: DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }


}