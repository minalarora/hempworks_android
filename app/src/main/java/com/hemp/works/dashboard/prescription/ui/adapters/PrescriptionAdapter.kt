package com.hemp.works.dashboard.prescription.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.model.Prescription
import com.hemp.works.dashboard.prescription.ui.PrescriptionItemClickListener
import com.hemp.works.databinding.ItemPrescriptionBinding
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
val dateFormat = SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
class PrescriptionAdapter(private val listener: PrescriptionItemClickListener) : ListAdapter<Prescription, PrescriptionAdapter.ViewHolder>(PrescriptionDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemPrescriptionBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Prescription, listener: PrescriptionItemClickListener) {
            binding.title.text = item.description
            binding.subTitle.text = dateFormat.format(item.date!!)
            binding.root.setOnClickListener {
                listener.onItemClick(item.prescription, item.type)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemPrescriptionBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class PrescriptionDiffCallback: DiffUtil.ItemCallback<Prescription>() {

    override fun areItemsTheSame(oldItem: Prescription, newItem: Prescription): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Prescription, newItem: Prescription): Boolean {
        return oldItem == newItem
    }


}