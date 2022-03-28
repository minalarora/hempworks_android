package com.hemp.works.dashboard.home.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.model.Product
import com.hemp.works.dashboard.product.ui.ProductItemClickListener
import com.hemp.works.databinding.ItemProductBinding

class ProductAdapter(private val listener: ProductItemClickListener) : ListAdapter<Product, ProductAdapter.ViewHolder>(ProductDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Product, listener: ProductItemClickListener) {
            binding.viewmodel = ProductViewModel(binding.root.context, item)
            binding.root.setOnClickListener {
                listener.onProductClick(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
                binding.root.clipToOutline = true
                return ViewHolder(binding)
            }
        }
    }
}


class ProductDiffCallback: DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }


}