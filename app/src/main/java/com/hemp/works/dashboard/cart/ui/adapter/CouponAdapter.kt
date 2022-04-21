package com.hemp.works.dashboard.cart.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.cart.ui.CouponItemClickListener
import com.hemp.works.dashboard.model.Coupon
import com.hemp.works.databinding.ItemCouponBinding

class CouponAdapter(private val listener: CouponItemClickListener, private val isCoupon: Boolean = true) : ListAdapter<Coupon, CouponAdapter.ViewHolder>(CouponDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item, listener, isCoupon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemCouponBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Coupon, listener: CouponItemClickListener, isCoupon: Boolean) {
            binding.viewmodel = CouponViewModel(binding.root.context, item, isCoupon)
            if (isCoupon) {
                binding.apply.setOnClickListener {
                    listener.onItemClick(item)
                }
            } else {
                binding.root.setOnClickListener {
                    listener.onItemClick(item)
                }
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCouponBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class CouponDiffCallback: DiffUtil.ItemCallback<Coupon>() {

    override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
        return oldItem == newItem
    }


}