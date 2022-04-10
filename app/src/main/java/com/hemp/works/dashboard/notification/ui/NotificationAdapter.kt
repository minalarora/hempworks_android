package com.hemp.works.dashboard.notification.ui

import android.annotation.SuppressLint
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.R
import com.hemp.works.base.Constants
import com.hemp.works.dashboard.model.Notification
import com.hemp.works.dashboard.model.Prescription
import com.hemp.works.dashboard.prescription.ui.PrescriptionItemClickListener
import com.hemp.works.databinding.ItemNotificationBinding
import com.hemp.works.databinding.ItemPrescriptionBinding
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
val dateFormat = SimpleDateFormat(Constants.ONLY_DATE_FORMAT);
class NotificationAdapter() : ListAdapter<Notification, NotificationAdapter.ViewHolder>(NotificationDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemNotificationBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Notification) {
            binding.title.text = item.text.toString()
            binding.date.text = if(DateUtils.isToday(item.date?.time ?: 0))
                binding.root.context.getString(R.string.today) else dateFormat.format(item.date!!)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNotificationBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class NotificationDiffCallback: DiffUtil.ItemCallback<Notification>() {

    override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem == newItem
    }


    override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
        return oldItem.text == newItem.text
    }


}