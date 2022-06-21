package com.minal.admin.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ResponsePresList
import com.minal.admin.databinding.LayoutDoctorCardBinding
import com.minal.admin.databinding.LayoutPrescriptionBinding
import com.minal.admin.utils.DoctorCardListener
import com.minal.admin.utils.FileUtils
import com.minal.admin.utils.PrescripListener

class PrescriptionAdapter(val mContext: Context, val mPrescripListener: PrescripListener,
                          private var mItemList: ArrayList<ResponsePresList> = arrayListOf(),
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val rowMeetingMember =
            LayoutPrescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ProductCategoryViewHolder(rowMeetingMember)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val dataItems = mItemList[position]

        when (holder) {

            is ProductCategoryViewHolder -> {
                holder.bindData(dataItems,mPrescripListener,mContext)



            }
        }
    }

    fun addItems(data:List<ResponsePresList>?) {
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

    class ProductCategoryViewHolder(var mBinding: LayoutPrescriptionBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bindData(dataItems: ResponsePresList?, mPrescripListener: PrescripListener, mContext: Context) {

            dataItems?.apply {
                mBinding.apply {
                    idTvOId.text = description
                    idTvTime.text = createdAt
                }
            }

            mBinding.idIgDownload.setOnClickListener {
                mPrescripListener.dataPass(adapterPosition,it,dataItems)
            }




        }
    }

    override fun getItemCount(): Int = mItemList.size

}