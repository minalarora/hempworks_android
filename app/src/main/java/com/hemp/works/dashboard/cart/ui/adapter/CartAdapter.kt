package com.hemp.works.dashboard.cart.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.cart.ui.CartItemClickListener
import com.hemp.works.dashboard.model.CartProduct
import com.hemp.works.databinding.ItemCartBinding

class CartAdapter(private val listener: CartItemClickListener) : ListAdapter<CartProduct, CartAdapter.ViewHolder>(CartDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: CartProduct, listener: CartItemClickListener) {
            binding.viewmodel = CartViewModel(item, listener)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCartBinding.inflate(layoutInflater, parent, false)
                binding.root.clipToOutline = true
                return ViewHolder(binding)
            }
        }
    }
}


class CartDiffCallback: DiffUtil.ItemCallback<CartProduct>() {

    override fun areItemsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
        return oldItem.productid == newItem.productid && oldItem.variantid == newItem.variantid
    }


    override fun areContentsTheSame(oldItem: CartProduct, newItem: CartProduct): Boolean {
        return oldItem == newItem
    }


}