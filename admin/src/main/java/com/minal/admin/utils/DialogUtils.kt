package com.minal.admin.utils

import android.app.Dialog
import android.content.Context
import com.minal.admin.R
import java.lang.ref.WeakReference

object DialogUtils {

    fun loader(context: Context, isCancel: Boolean = false): Dialog {
        val weakref = WeakReference(context)
        val dialog = Dialog(weakref.get()!!, R.style.MaterialDialog)
        dialog.setContentView(R.layout.layout_loading)
        dialog.setCancelable(isCancel)
        dialog.setCanceledOnTouchOutside(isCancel)
        return dialog
    }
}