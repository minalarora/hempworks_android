package com.hemp.works.utils

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.hemp.works.base.Constants
import com.hemp.works.login.data.model.Admin
import com.hemp.works.login.data.model.Doctor
import kotlin.Exception

class PreferenceManagerUtil {
    companion object {

        @JvmStatic
        fun putString(context: Context, key: String, value: String?) {
            PreferenceManager.getDefaultSharedPreferences(context)?.let {
                with(it.edit()) {
                    putString(key, value)
                    commit()
                }
            }
        }

        @JvmStatic
        fun getString(context: Context, key: String): String? {

          return  PreferenceManager.getDefaultSharedPreferences(context)?.let {
                return@let it.getString(key, "")
            }
        }


        @JvmStatic
        fun putDoctor(context: Context, doctor: Doctor): Boolean {

            return try {
                PreferenceManager.getDefaultSharedPreferences(context)?.let {
                    with(it.edit()) {
                        putString(Constants.DOCTOR, Gson().toJson(doctor))
                        commit()
                    }
                }
                true
            } catch (ex: Exception) {
                false
            }
        }

        @JvmStatic
        fun getDoctor(context: Context): Doctor? {
            try {
                return  PreferenceManager.getDefaultSharedPreferences(context)?.let {
                    return@let Gson().fromJson(it.getString(Constants.DOCTOR, ""), Doctor::class.java)
                }
            } catch (ex: Exception) {
                return null
            }
        }

        @JvmStatic
        fun putAdmin(context: Context, admin: Admin): Boolean {

            return try {
                PreferenceManager.getDefaultSharedPreferences(context)?.let {
                    with(it.edit()) {
                        putString(Constants.ADMIN, Gson().toJson(admin))
                        commit()
                    }
                }
                true
            } catch (ex: Exception) {
                false
            }
        }

        @JvmStatic
        fun getAdmin(context: Context): Admin? {
            try {
                return  PreferenceManager.getDefaultSharedPreferences(context)?.let {
                    return@let Gson().fromJson(it.getString(Constants.ADMIN, ""), Admin::class.java)
                }
            } catch (ex: Exception) {
                return null
            }
        }

        @JvmStatic
        fun clear(context: Context): Boolean {
            return try {
                PreferenceManager.getDefaultSharedPreferences(context)?.let {
                    with(it.edit()) {
                        putString(Constants.ADMIN, "")
                        putString(Constants.DOCTOR, "")
                        putString(Constants.AUTH_TOKEN, "")
                        commit()
                    }
                }
                true
            } catch (ex: Exception) {
                false
            }
        }

    }
}