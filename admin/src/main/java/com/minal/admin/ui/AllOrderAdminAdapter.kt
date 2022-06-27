package com.minal.admin.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minal.admin.data.response.OrderList
import com.minal.admin.databinding.LayoutOrderBinding
import com.minal.admin.utils.CalendarUtils
import com.minal.admin.utils.OrderListener

class AllOrderAdminAdapter(val mContext: Context, val mOrderListener: OrderListener,
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

    class ProductCategoryViewHolder(var mBinding: LayoutOrderBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bindData(dataItems: OrderList?, mOrderListener: OrderListener, mContext: Context) {

            dataItems?.apply {
                mBinding.apply {
                    idTvOId.text = id.toString()
                    idTvStatus.text = status
                    idTvTime.text = CalendarUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss", "dd MMM yyyy , hh:mm a")
                    if (transactionid == null){
                        idTvTransactionId.text = "Any"
                    }
                    else{
                        idTvTransactionId.text = transactionid.toString()

                    }

                    idTvPrice.text = totalprice.toString()
                }
            }

            mBinding.idRlOrder.setOnClickListener {
                mOrderListener.dataPass(adapterPosition,it,dataItems)
            }



        }
    }

    override fun getItemCount(): Int = mItemList.size

}