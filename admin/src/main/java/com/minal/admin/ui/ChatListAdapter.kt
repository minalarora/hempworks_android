package com.minal.admin.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ResponseChatList
import com.minal.admin.databinding.LayoutChatListBinding
import com.minal.admin.databinding.LayoutOrderBinding
import com.minal.admin.utils.CalendarUtils
import com.minal.admin.utils.ChatListener
import com.minal.admin.utils.OrderListener

class ChatListAdapter(val mContext: Context, val mChatListener: ChatListener,
                      private var mItemList: ArrayList<ResponseChatList> = arrayListOf(),
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val rowMeetingMember =
            LayoutChatListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  ProductCategoryViewHolder(rowMeetingMember)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val dataItems = mItemList[position]

        when (holder) {

            is ProductCategoryViewHolder -> {
                holder.bindData(dataItems,mChatListener,mContext)

            }
        }
    }

    fun addItems(data:List<ResponseChatList>?) {
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

    class ProductCategoryViewHolder(var mBinding: LayoutChatListBinding) :
        RecyclerView.ViewHolder(mBinding.root) {

        fun bindData(dataItems: ResponseChatList?, mChatListener: ChatListener, mContext: Context) {

            dataItems?.apply {
                mBinding.apply {

                    if (dataItems.doctor == null){
                        mBinding.idTvName.text = "Anonymous"
                    }
                    else{
                        mBinding.idTvName.text = dataItems?.doctor.name

                    }

                    if (dataItems?.messages == null)
                        mBinding.idTvMsg.text = " "
                    else{
                        if (dataItems?.messages?.isDoctor == true)
                            mBinding.idTvMsg.text = dataItems?.messages.message

                        else
                            mBinding.idTvMsg.text = "${"You:"} ${dataItems?.messages.message}"

                    }
                }
            }

            mBinding.idRlSingle.setOnClickListener {
                mChatListener.dataPass(adapterPosition,it,dataItems)
            }



        }
    }

    override fun getItemCount(): Int = mItemList.size

}