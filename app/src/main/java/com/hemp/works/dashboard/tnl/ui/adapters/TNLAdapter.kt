package com.hemp.works.dashboard.tnl.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hemp.works.dashboard.home.ui.adapters.ProductViewModel
import com.hemp.works.dashboard.model.LiveSession
import com.hemp.works.dashboard.model.NewsLetter
import com.hemp.works.dashboard.model.Product
import com.hemp.works.dashboard.model.Tutorial
import com.hemp.works.dashboard.product.ui.ProductItemClickListener
import com.hemp.works.dashboard.tnl.ui.TNLItemClickListener
import com.hemp.works.dashboard.tnl.ui.TNLType
import com.hemp.works.databinding.ItemLivesessionBinding
import com.hemp.works.databinding.ItemNewsletterBinding
import com.hemp.works.databinding.ItemProductBinding
import com.hemp.works.databinding.ItemTutorialBinding
import java.lang.UnsupportedOperationException

class TNLAdapter(private val listener: TNLItemClickListener, private val listType: TNLType) : ListAdapter<Any, RecyclerView.ViewHolder>(TNLDiffCallback()) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(holder.adapterPosition)
        when(listType) {
            TNLType.TUTORIAL -> {
                holder as TViewHolder
                holder.bind(item as Tutorial, listener)
            }
            TNLType.NEWSLETTER -> {
                holder as NViewHolder
                holder.bind(item as NewsLetter, listener)
            }
            TNLType.LIVESESSION -> {
                holder as LViewHolder
                holder.bind(item as LiveSession, listener)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(listType) {
            TNLType.TUTORIAL -> TViewHolder.from(parent)
            TNLType.NEWSLETTER -> NViewHolder.from(parent)
            TNLType.LIVESESSION -> LViewHolder.from(parent)
        }
    }

    class TViewHolder private constructor(val binding: ItemTutorialBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: Tutorial, listener: TNLItemClickListener) {
            binding.viewmodel = TutorialViewModel(item)
            binding.pdfDownload.setOnClickListener {
                listener.onTutorialClick(item)
            }
            binding.coverContainer.setOnClickListener {
                listener.onTutorialClick(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): TViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTutorialBinding.inflate(layoutInflater, parent, false)
                return TViewHolder(binding)
            }
        }
    }

    class NViewHolder private constructor(val binding: ItemNewsletterBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: NewsLetter, listener: TNLItemClickListener) {
            binding.viewmodel = NewsLetterViewModel(item)
            binding.pdfDownload.setOnClickListener {
                listener.onNewsLetterClick(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsletterBinding.inflate(layoutInflater, parent, false)
                return NViewHolder(binding)
            }
        }
    }

    class LViewHolder private constructor(val binding: ItemLivesessionBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: LiveSession, listener: TNLItemClickListener) {
            binding.viewmodel = LiveSessionViewModel(item)
            binding.root.setOnClickListener {
                listener.onLiveSessionClick(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): LViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemLivesessionBinding.inflate(layoutInflater, parent, false)
                return LViewHolder(binding)
            }
        }
    }
}


class TNLDiffCallback: DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is Tutorial && newItem is Tutorial) {
            oldItem.id == newItem.id
        } else if (oldItem is NewsLetter && newItem is NewsLetter) {
            oldItem.id == newItem.id
        } else if (oldItem is LiveSession && newItem is LiveSession) {
            oldItem.id == newItem.id
        } else {
            throw UnsupportedOperationException()
        }

    }


    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return if (oldItem is Tutorial && newItem is Tutorial) {
            oldItem == newItem
        } else if (oldItem is NewsLetter && newItem is NewsLetter) {
            oldItem == newItem
        } else if (oldItem is LiveSession && newItem is LiveSession) {
            oldItem == newItem
        } else {
            throw UnsupportedOperationException()
        }
    }


}