package com.minal.admin.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.minal.admin.R
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ResponseAllDoctors
import com.minal.admin.databinding.LayoutDoctorCardBinding
import com.minal.admin.databinding.LayoutOrderBinding
import com.minal.admin.utils.CalendarUtils
import com.minal.admin.utils.DoctorCardListener
import com.minal.admin.utils.OrderListener

class AllOrderAdapter(val mContext: Context, val mOrderListener: OrderListener,
                      private var mItemList: ArrayList<OrderList> = arrayListOf(),
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val rowMeetingMember =
            LayoutOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ProductCategoryViewHolder(rowMeetingMember)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val dataItems = mItemList[position]

        when (holder) {

            is ProductCategoryViewHolder -> {
                holder.bindData(dataItems,mOrderListener,mContext)

            }
        }
    }

    fun addItems(data:List<OrderList>?) {
        data?.let{
            this.mItemList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        mItemList.clear()
        notifyDataSetChanged()
    }

    class ProductCategoryViewHolder(var mBinding: LayoutOrderBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bindData(dataItems: OrderList?, mOrderListener: OrderListener, mContext: Context) {

             dataItems?.apply {
                 mBinding.apply {
                     idTvOId.text = id.toString()
                     idTvStatus.text = status
                     idTvTime.text = CalendarUtils.formatDate(createdAt, "yyyy-MM-dd HH:mm:ss", "MMM dd, hh:mm a")
                     idTvName.text = order.getOrNull(0)?.productname
                     idTvSize.text = order.getOrNull(0)?.variantname
                     idTvPrice.text = order.getOrNull(0)?.price.toString()
                     idTvQuantity.text = order.getOrNull(0)?.quantity.toString()
                 }
             }

            mBinding.idRlOrder.setOnClickListener {
                mOrderListener.dataPass(adapterPosition,it,dataItems)
            }



        }
    }

    override fun getItemCount(): Int = mItemList.size

}