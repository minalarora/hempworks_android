package com.hemp.works.dashboard.wallet.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.model.Transaction
import com.hemp.works.dashboard.model.WalletHistory
import com.hemp.works.dashboard.payment.ui.adapters.PaymentHistoryViewModel
import com.hemp.works.databinding.ItemPaymentHistoryBinding
import com.hemp.works.databinding.ItemWalletBinding

class WalletAdapter() : ListAdapter<WalletHistory, WalletAdapter.ViewHolder>(WalletDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemWalletBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: WalletHistory) {
            binding.viewmodel = WalletHistoryViewModel(binding.root.context, item)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemWalletBinding.inflate(layoutInflater, parent, false)
                binding.root.clipToOutline = true
                return ViewHolder(binding)
            }
        }
    }
}


class WalletDiffCallback: DiffUtil.ItemCallback<WalletHistory>() {
    override fun areItemsTheSame(oldItem: WalletHistory, newItem: WalletHistory): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: WalletHistory, newItem: WalletHistory): Boolean {
        return oldItem.id == newItem.id
    }


}