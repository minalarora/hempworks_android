package com.hemp.works.dashboard.home.ui.adapters

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hemp.works.R
import com.hemp.works.base.MyAppGlideModule
import com.hemp.works.dashboard.home.ui.CategoryItemClickListener
import com.hemp.works.dashboard.model.Category
import com.hemp.works.databinding.ItemCategoryBinding

class CategoryAdapter(private val listener: CategoryItemClickListener) : ListAdapter<Category, CategoryAdapter.ViewHolder>(CategoryDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        holder.bind(item, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Category, listener: CategoryItemClickListener) {
            Glide.with(binding.root.context)
                .load(item.image)
                .placeholder(ColorDrawable(ContextCompat.getColor(binding.root.context, R.color.grey_AAAAAA)))
                .error(ColorDrawable(ContextCompat.getColor(binding.root.context, R.color.grey_AAAAAA)))
                .apply(MyAppGlideModule.requestOptions)
                .into(binding.image);

            binding.title.text = item.category
            binding.root.setOnClickListener {
                listener.onItemClick(item.id)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCategoryBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}


class CategoryDiffCallback: DiffUtil.ItemCallback<Category>() {

    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }


}