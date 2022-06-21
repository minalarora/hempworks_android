package com.minal.admin.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minal.admin.data.response.ResponseAllDoctors
import com.minal.admin.data.response.ResponseDoctorDetail
import com.minal.admin.databinding.LayoutDoctorCardBinding
import com.minal.admin.databinding.LayoutOfDiscountBinding

class DiscountAdapter(private var mItemList: ArrayList<ResponseDoctorDetail.Discount> =
                            arrayListOf()): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val rowMeetingMember =
            LayoutOfDiscountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    fun addItems(data:List<ResponseDoctorDetail.Discount>?) {
        this.mItemList.clear()

        data?.let{
            this.mItemList.addAll(it)
            notifyDataSetChanged()
        }
    }
    fun clear() {
        mItemList.clear()
        notifyDataSetChanged()
    }

    class ProductCategoryViewHolder(var mBinding: LayoutOfDiscountBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bindData(dataItems: ResponseDoctorDetail.Discount?) {

            dataItems?.apply {
                mBinding.apply {
                    idTvPrice.text = price.toString()
                    idTvPercent.text = percentage.toString()
                }
            }
        }
    }

    override fun getItemCount(): Int = mItemList.size

}