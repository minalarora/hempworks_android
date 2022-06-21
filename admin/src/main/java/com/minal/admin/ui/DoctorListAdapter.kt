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
import com.minal.admin.data.response.ResponseAllDoctors
import com.minal.admin.data.response.ResponseProduct
import com.minal.admin.databinding.LayoutDoctorCardBinding
import com.minal.admin.databinding.LayoutProductNameBinding
import com.minal.admin.utils.DoctorCardListener

class DoctorListAdapter(val mContext: Context,val mDoctorCardListener: DoctorCardListener,private var mItemList: ArrayList<ResponseAllDoctors> =
                         arrayListOf(),
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val rowMeetingMember =
            LayoutDoctorCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ProductCategoryViewHolder(rowMeetingMember)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val dataItems = mItemList[position]

        when (holder) {

            is ProductCategoryViewHolder -> {
                holder.bindData(dataItems,mDoctorCardListener,mContext)

            }
        }
    }

    fun addItems(data:List<ResponseAllDoctors>?) {
        this.mItemList.clear()
        data?.let{
            this.mItemList.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun getItemList() = mItemList


    fun clear() {
        mItemList.clear()
        notifyDataSetChanged()
    }

    class ProductCategoryViewHolder(var mBinding: LayoutDoctorCardBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bindData(dataItems: ResponseAllDoctors?,mDoctorCardListener:DoctorCardListener,mContext:Context) {

            mBinding.apply {
                idTvName.text = dataItems?.name
                idTvId.text = dataItems?.id
                idTvClinic.text = dataItems?.clinic
                idTvEmail.text = dataItems?.email
                idTvMobile.text = dataItems?.mobile
            }

            Glide.with(mContext).load(dataItems?.profile)
                .into(object : CustomTarget<Drawable>() {
                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        Log.d("url", "error")
                        mBinding.idIgProfile.setImageResource(R.drawable.ic_photo)
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                        mBinding.idIgProfile.setImageDrawable(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })

            mBinding.idRlDoctor.setOnClickListener {
                mDoctorCardListener.dataPass(adapterPosition,it,dataItems)
            }

        }
    }

    override fun getItemCount(): Int = mItemList.size

}