package com.minal.admin

import android.icu.text.Transliterator
import android.view.View
import com.minal.admin.ui.DataArray
import java.text.FieldPosition

interface AdminWorkListener {
    fun dataPass(position: Int?=null,v:View?=null,data:DataArray?=null)
}