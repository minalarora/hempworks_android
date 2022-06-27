package com.minal.admin.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ResponseSingleOrder
import com.minal.admin.databinding.LayoutOrderBinding
import com.minal.admin.databinding.LayoutProductOfOrderBinding
import com.minal.admin.utils.CalendarUtils
import com.minal.admin.utils.OrderListener

class SingleOrderListAdapter(val mContext: Context,
                      private var mItemList: ArrayList<ResponseSingleOrder.Order> = arrayListOf(),
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val rowMeetingMember =
            LayoutProductOfOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ProductCategoryViewHolder(rowMeetingMember)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val dataItems = mItemList[position]

        when (holder) {

            is ProductCategoryViewHolder -> {
                holder.bindData(dataItems,mContext)

            }
        }
    }

    fun addItems(data:List<ResponseSingleOrder.Order>?) {
        data?.let{
            this.mItemList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun clear() {
        mItemList.clear()
        notifyDataSetChanged()
    }

    class ProductCategoryViewHolder(var mBinding: LayoutProductOfOrderBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bindData(dataItems: ResponseSingleOrder.Order?,  mContext: Context) {

            dataItems?.apply {
                mBinding.apply {
                    idTvOId.text = dataItems?.productid.toString()
                    idTvNames.text = dataItems?.productname

                    idTvPrice.text = dataItems?.price.toString()
                    idTvQuantity.text = dataItems?.quantity.toString()
                    idTvSize.text = dataItems?.variantname
                    idTvVarId.text = dataItems?.variantid.toString()
                }
            }
        }
    }

    override fun getItemCount(): Int = mItemList.size

}