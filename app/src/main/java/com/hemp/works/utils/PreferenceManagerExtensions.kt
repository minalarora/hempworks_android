package com.hemp.works.utils

import android.content.Context
import androidx.preference.PreferenceManager

class PreferenceManagerUtil {
    companion object {

        @JvmStatic
        fun putString(context: Context, key: String, value: String?) {
            PreferenceManager.getDefaultSharedPreferences(context)?.let {
                with(it.edit()) {
                    putString(key, value)
                    apply()
                }
            }
        }

        @JvmStatic
        fun getString(context: Context, key: String): String? {

          return  PreferenceManager.getDefaultSharedPreferences(context)?.let {
                return@let it.getString(key, "")
            }
        }
    }
}