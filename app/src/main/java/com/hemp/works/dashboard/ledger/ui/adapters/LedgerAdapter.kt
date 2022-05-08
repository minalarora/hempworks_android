package com.hemp.works.dashboard.ledger.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.credit.ui.CreditHistoryViewModel
import com.hemp.works.dashboard.model.*
import com.hemp.works.dashboard.order.ui.OrderItemClickListener
import com.hemp.works.dashboard.order.ui.adapters.OrderViewModel
import com.hemp.works.dashboard.payment.ui.adapters.PaymentHistoryViewModel
import com.hemp.works.dashboard.wallet.ui.adapters.WalletHistoryViewModel
import com.hemp.works.databinding.ItemCreditBinding
import com.hemp.works.databinding.ItemOrderBinding
import com.hemp.works.databinding.ItemPaymentHistoryBinding
import com.hemp.works.databinding.ItemWalletBinding

class LedgerAdapter(private var list: ArrayList<Any> = mutableListOf<Any>() as ArrayList<Any>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when(list[position]) {
            is Order -> 1
            is Transaction -> 2
            is CreditHistory -> 3
            is WalletHistory -> 4
            else -> throw UnsupportedOperationException()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            1 -> OrderViewHolder.from(parent)
            2 -> PaymentViewHolder.from(parent)
            3 -> CreditViewHolder.from(parent)
            4 -> WalletViewHolder.from(parent)
            else -> throw UnsupportedOperationException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = list[holder.adapterPosition]) {
            is Order -> (holder as OrderViewHolder).bind(item)
            is Transaction -> (holder as PaymentViewHolder).bind(item)
            is CreditHistory -> (holder as CreditViewHolder).bind(item)
            is WalletHistory -> (holder as WalletViewHolder).bind(item)
            else -> throw UnsupportedOperationException()
        }
    }

    class OrderViewHolder private constructor(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Order) {
            binding.viewmodel = OrderViewModel(binding.root.context, item, null)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): OrderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemOrderBinding.inflate(layoutInflater, parent, false)
                binding.root.clipToOutline = true
                return OrderViewHolder(binding)
            }
        }
    }

    class PaymentViewHolder private constructor(val binding: ItemPaymentHistoryBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Transaction) {
            binding.viewmodel = PaymentHistoryViewModel(binding.root.context, item)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): PaymentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPaymentHistoryBinding.inflate(layoutInflater, parent, false)
                binding.root.clipToOutline = true
                return PaymentViewHolder(binding)
            }
        }
    }

    class CreditViewHolder private constructor(val binding: ItemCreditBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: CreditHistory) {
            binding.viewmodel = CreditHistoryViewModel(binding.root.context, item)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CreditViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCreditBinding.inflate(layoutInflater, parent, false)
                binding.root.clipToOutline = true
                return CreditViewHolder(binding)
            }
        }
    }

    class WalletViewHolder private constructor(val binding: ItemWalletBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: WalletHistory) {
            binding.viewmodel = WalletHistoryViewModel(binding.root.context, item)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): WalletViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemWalletBinding.inflate(layoutInflater, parent, false)
                binding.root.clipToOutline = true
                return WalletViewHolder(binding)
            }
        }
    }

    fun submitList(newList: ArrayList<Any>) {
        list.clear()
        list.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
