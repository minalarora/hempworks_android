package com.minal.admin.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minal.admin.AdminWorkListener
import com.minal.admin.data.request.ReqAddManualOrder
import com.minal.admin.data.response.ProductList
import com.minal.admin.databinding.LayoutAdminButtonBinding
import com.minal.admin.databinding.LayoutManualOrderItemBinding

class ManualOrderItemAdapter(private val listdata: ArrayList<ProductList?>) :
    RecyclerView.Adapter<ManualOrderItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutManualOrderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)

    }

    override fun getItemCount(): Int = listdata?.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=listdata?.get(position)

            holder.setContactName( data)

    }

    class ViewHolder(var mBinding: LayoutManualOrderItemBinding) :
        RecyclerView.ViewHolder(mBinding.root) {


        fun setContactName( data:ProductList?) {

            Log.d("type",data?.variantSize.toString())
            mBinding.idTvName.text = data?.variantName
            mBinding.idTvSize.text = "${data?.variantSize.toString()} ${data?.variantType}"
            mBinding.idTvQunat.text = data?.quantity.toString()
            mBinding.idTvPrice.text = data?.price.toString()
        }
    }



}