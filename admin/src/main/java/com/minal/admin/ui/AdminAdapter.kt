package com.minal.admin.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minal.admin.AdminWorkListener
import com.minal.admin.databinding.LayoutAdminButtonBinding

class AdminAdapter(private val listdata: ArrayList<DataArray>?,val mAdminWorkListener: AdminWorkListener) :
    RecyclerView.Adapter<AdminAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {


        val view = LayoutAdminButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val title: String? = listdata?.get(position)?.name
        Log.d("title",title.toString())
        val data=listdata?.get(position)

        Log.d("TAG", title.toString())
        if (title != null) {

                holder.setContactName( title,data,mAdminWorkListener)

        }
    }

    class ViewHolder(var mBinding: LayoutAdminButtonBinding) :
        RecyclerView.ViewHolder(mBinding.root) {


        fun setContactName( name: String,data:DataArray?,mAdminWorkListener: AdminWorkListener) {
            mBinding.apply {
                idTvTransport.text = name
            }

            mBinding.cardViewImg.setOnClickListener {
                mAdminWorkListener.dataPass(adapterPosition,it,data)
            }

        }
    }


}