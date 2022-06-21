package com.minal.admin.utils

import android.view.View
import com.minal.admin.data.response.ResponseAllDoctors

interface DoctorCardListener {

    fun dataPass(position:Int?=null,v:View?=null,data:ResponseAllDoctors?=null)
}