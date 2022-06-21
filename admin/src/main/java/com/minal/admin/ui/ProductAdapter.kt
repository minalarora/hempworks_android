package com.minal.admin.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minal.admin.data.response.ResponseProduct
import com.minal.admin.databinding.LayoutProductNameBinding

class ProductAdapter(private var mItemList: ArrayList<ResponseProduct.Variant> =
    arrayListOf()): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val rowMeetingMember =
            LayoutProductNameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ProductCategoryViewHolder(rowMeetingMember)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val dataItems = mItemList[position]

        when (holder) {

            is ProductCategoryViewHolder -> {
                holder.bindData(dataItems)

            }
        }
    }

    fun addItems(data:List<ResponseProduct.Variant?>?) {
        data?.let{
            this.mItemList.clear()
            notifyDataSetChanged()
        }
    }
    fun clear() {
        mItemList.clear()
        notifyDataSetChanged()
    }

    class ProductCategoryViewHolder(var mBinding: LayoutProductNameBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bindData(dataItems: ResponseProduct.Variant?) {


        }
    }

    override fun getItemCount(): Int = mItemList.size

}