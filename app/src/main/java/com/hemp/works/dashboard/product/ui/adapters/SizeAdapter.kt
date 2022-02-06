package com.hemp.works.dashboard.product.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.model.Variant
import com.hemp.works.databinding.ItemSizeBinding
import com.hemp.works.utils.strike

class SizeAdapter : ListAdapter<Variant, SizeAdapter.ViewHolder>(SizeDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

   class ViewHolder private constructor(val binding: ItemSizeBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(variant: Variant) {
            binding.size.text = "${variant.size} ${variant.type}"
            binding.size.strike = !variant.instock
            binding.variant = variant
            binding.root.setOnClickListener {
                //TODO: IMPLEMENT SELECTION LISTENER FOR SIZE
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSizeBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

//    fun updateList(list: List<Variant>){
//        if (list.size == 1) {
//            list[0].isSelected = true
//            submitList(list)
//        }
//    }
//
//    fun doSelection(variant: Variant) {
//
//    }
//
//    fun getSelected() : Variant? {
//        return currentList.find { it.isSelected }
//    }
}


class SizeDiffCallback : DiffUtil.ItemCallback<Variant>() {

    override fun areItemsTheSame(oldItem: Variant, newItem: Variant): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Variant, newItem: Variant): Boolean {
        return oldItem.isSelected.get() == newItem.isSelected.get()
    }


}