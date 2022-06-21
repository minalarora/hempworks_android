package com.minal.admin.utils

import android.view.View
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ResponseAllDoctors

interface  OrderListener{

    fun dataPass(position:Int?=null, v: View?=null, data: OrderList?=null)
}