package com.minal.admin.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minal.admin.data.response.Message
import com.minal.admin.data.response.RespSingleChatList
import com.minal.admin.data.response.ResponseChatList
import com.minal.admin.databinding.LayoutItemRecieveBinding
import com.minal.admin.databinding.LayoutItemSendBinding

class ChatAdapter(private val listener: SupportItemClickListener) : ListAdapter<RespSingleChatList.Message, RecyclerView.ViewHolder>(SupportDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        if(holder is ChatAdapter.SentViewHolder) {
            holder as ChatAdapter.SentViewHolder
            holder.bind(item, listener)
        } else {
            holder as ChatAdapter.ReceivedViewHolder
            holder.bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).isDoctor == false) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            ChatAdapter.SentViewHolder.from(parent)
        } else {
            ChatAdapter.ReceivedViewHolder.from(parent)
        }
    }


    class SentViewHolder private constructor(val binding: LayoutItemSendBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(message: RespSingleChatList.Message, listener: SupportItemClickListener) {
            binding.messageText.text = message.message.toString()
//            binding.error.setOnClickListener {
//                listener.onRetry(message)
//            }
//            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemSendBinding.inflate(layoutInflater, parent, false)
                return SentViewHolder(binding)
            }
        }
    }

    class ReceivedViewHolder private constructor(val binding: LayoutItemRecieveBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(message: RespSingleChatList.Message) {
            binding.messageText.text = message.message.toString()
//            binding.message = message
//            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ReceivedViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutItemRecieveBinding.inflate(layoutInflater, parent, false)
                return ReceivedViewHolder(binding)
            }
        }
    }

}


class SupportDiffCallback : DiffUtil.ItemCallback<RespSingleChatList.Message>() {
    override fun areItemsTheSame(oldItem: RespSingleChatList.Message, newItem: RespSingleChatList.Message): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: RespSingleChatList.Message, newItem: RespSingleChatList.Message): Boolean {
        return oldItem.message.equals(newItem.message) && oldItem.date?.equals(newItem.date) == true
    }


}