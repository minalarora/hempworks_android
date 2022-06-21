//package com.minal.admin.ui
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.minal.admin.data.response.Message
//import com.minal.admin.databinding.LayoutItemReceiveBinding
//import com.minal.admin.databinding.LayoutItemSentBinding
//
//class SingleChatAdapter(private val listener: ChatItemClickListener) : ListAdapter<Message, RecyclerView.ViewHolder>(SupportDiffCallback()) {
//
//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val item = getItem(holder.adapterPosition)
//        if(holder is SentViewHolder) {
//            holder as SentViewHolder
//            holder.bind(item, listener)
//        } else {
//            holder as SingleChatAdapter.ReceivedViewHolder
//            holder.bind(item)
//        }
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return if(getItem(position).isDoctor == true) 0 else 1
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return if (viewType == 0) {
//            SentViewHolder.from(parent)
//        } else {
//            ReceivedViewHolder.from(parent)
//        }
//    }
//
//    class SentViewHolder private constructor(val binding: LayoutItemSentBinding) : RecyclerView.ViewHolder(binding.root){
//
//        fun bind(message: Message, listener: ChatItemClickListener) {
//            binding.messageText.text = message.message.toString()
//            binding.error.setOnClickListener {
//                listener.onRetry(message)
//            }
//        }
//
//        companion object {
//            fun from(parent: ViewGroup): SentViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = LayoutItemSentBinding.inflate(layoutInflater, parent, false)
//                return SentViewHolder(binding)
//            }
//        }
//    }
//
//    class ReceivedViewHolder private constructor(val binding: LayoutItemReceiveBinding) : RecyclerView.ViewHolder(binding.root){
//
//        fun bind(message: Message) {
//            binding.messageText.text = message.message.toString()
////            binding.message = message
////            binding.executePendingBindings()
//        }
//
//        companion object {
//            fun from(parent: ViewGroup): ReceivedViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = LayoutItemReceiveBinding.inflate(layoutInflater, parent, false)
//                return ReceivedViewHolder(binding)
//            }
//        }
//    }
//
//}
//
//
//class SupportDiffCallback : DiffUtil.ItemCallback<Message>() {
//    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
//        return oldItem == newItem
//    }
//
//    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
//        return oldItem.message.equals(newItem.message) && oldItem.date?.equals(newItem.date) == true
//    }
//}