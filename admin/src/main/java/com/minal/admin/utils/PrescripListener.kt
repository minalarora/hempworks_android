package com.minal.admin.utils

import android.view.View
import com.minal.admin.data.response.OrderList
import com.minal.admin.data.response.ResponsePresList

interface PrescripListener {
    fun dataPass(position:Int?=null, v: View?=null, data: ResponsePresList?=null)

}