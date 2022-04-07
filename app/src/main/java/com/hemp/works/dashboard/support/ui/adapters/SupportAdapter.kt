package com.hemp.works.dashboard.support.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.model.*
import com.hemp.works.dashboard.support.ui.SupportItemClickListener
import com.hemp.works.databinding.ItemReceivedBinding
import com.hemp.works.databinding.ItemSentBinding

class SupportAdapter(private val listener: SupportItemClickListener) : ListAdapter<Message, RecyclerView.ViewHolder>(SupportDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        if(holder is SupportAdapter.SentViewHolder) {
                holder as SupportAdapter.SentViewHolder
                holder.bind(item, listener)
        } else {
            holder as SupportAdapter.ReceivedViewHolder
            holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).isDoctor == true) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            SupportAdapter.SentViewHolder.from(parent)
        } else {
            SupportAdapter.ReceivedViewHolder.from(parent)
        }
    }


    class SentViewHolder private constructor(val binding: ItemSentBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(message: Message, listener: SupportItemClickListener) {
            binding.messageText.text = message.message.toString()
            binding.message = message
            binding.error.setOnClickListener {
                listener.onRetry(message)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSentBinding.inflate(layoutInflater, parent, false)
                return SentViewHolder(binding)
            }
        }
    }

    class ReceivedViewHolder private constructor(val binding: ItemReceivedBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(message: Message) {
            binding.messageText.text = message.message.toString()
            binding.message = message
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ReceivedViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemReceivedBinding.inflate(layoutInflater, parent, false)
                return ReceivedViewHolder(binding)
            }
        }
    }

}


class SupportDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.message.equals(newItem.message) && oldItem.date?.equals(newItem.date) == true
    }


}