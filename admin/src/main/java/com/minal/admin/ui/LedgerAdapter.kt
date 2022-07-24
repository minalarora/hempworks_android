package com.minal.admin.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ledger.*
import com.minal.admin.databinding.ItemCreditBinding
import com.minal.admin.databinding.ItemPaymentHistoryBinding
import com.minal.admin.databinding.ItemWalletBinding
import com.minal.admin.databinding.LayoutOrderBinding
import com.minal.admin.utils.OrderListener


class LedgerAdapter(private var list: ArrayList<Ledger> = mutableListOf<Ledger>() as ArrayList<Ledger>,
                    val mOrderListener: OrderListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when(list[position].value) {
            is OrderList -> 1
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
        when(val item = list[holder.adapterPosition].value) {
            is OrderList -> (holder as OrderViewHolder).bind(item, mOrderListener)
            is Transaction -> (holder as PaymentViewHolder).bind(item)
            is CreditHistory -> (holder as CreditViewHolder).bind(item)
            is WalletHistory -> (holder as WalletViewHolder).bind(item)
            else -> throw UnsupportedOperationException()
        }
    }

    class OrderViewHolder private constructor(val binding: LayoutOrderBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: OrderList, mOrderListener: OrderListener) {
            item?.apply {
                binding.apply {
                    idTvOId.text = id.toString()
                    idTvStatus.text = status
                    idTvTime.text = date.toString()
                    idTvPayment.text= payment
                    idTvPrice.text = discountprice.toString()
                }
            }

            binding.idRlOrder.setOnClickListener {
                mOrderListener.dataPass(adapterPosition,it,item)
            }
//            binding.viewmodel = OrderViewModel(binding.root.context, item, null)
//            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): OrderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutOrderBinding.inflate(layoutInflater, parent, false)
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

    fun submitList(newList: List<Ledger>) {
        list.clear()
        list.addAll(newList)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
