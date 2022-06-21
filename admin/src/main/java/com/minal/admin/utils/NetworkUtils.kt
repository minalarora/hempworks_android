package com.minal.admin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.minal.admin.R
import com.minal.admin.ext_fun.showToast

object NetworkUtils {

        fun isOnline(context: Context, showToast: Boolean = true): Boolean {
            // Checking internet connectivity
            val connectivityMgr =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            var activeNetwork: NetworkInfo? = null
            if (connectivityMgr != null) {
                activeNetwork = connectivityMgr.activeNetworkInfo
            }
            if(activeNetwork == null && showToast){
                context.showToast(context.getString(R.string.no_internet_connection))
            }
            return activeNetwork != null
        }




}