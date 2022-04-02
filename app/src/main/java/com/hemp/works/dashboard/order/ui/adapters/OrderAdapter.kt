package com.hemp.works.dashboard.order.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.model.Order
import com.hemp.works.dashboard.order.ui.OrderItemClickListener
import com.hemp.works.databinding.ItemOrderBinding

class OrderAdapter(private val listener: OrderItemClickListener) : ListAdapter<Order, OrderAdapter.ViewHolder>(OrderDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Order, listener: OrderItemClickListener) {
            binding.viewmodel = OrderViewModel(binding.root.context, item, listener)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemOrderBinding.inflate(layoutInflater, parent, false)
                binding.root.clipToOutline = true
                return ViewHolder(binding)
            }
        }
    }
}


class OrderDiffCallback: DiffUtil.ItemCallback<Order>() {

    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.order?.get(0)?.productid == newItem.order?.get(0)?.productid &&
                oldItem.order?.get(0)?.variantid == newItem.order?.get(0)?.variantid &&
                oldItem.order?.get(0)?.price == newItem.order?.get(0)?.price &&
                oldItem.order?.get(0)?.quantity == newItem.order?.get(0)?.quantity
    }


    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.order?.get(0) == newItem.order?.get(0)
    }


}