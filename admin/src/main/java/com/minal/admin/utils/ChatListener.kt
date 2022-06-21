package com.minal.admin.utils

import android.view.View
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ResponseChatList

interface ChatListener {
    fun dataPass(position:Int?=null, v: View?=null, data: ResponseChatList?=null)

}