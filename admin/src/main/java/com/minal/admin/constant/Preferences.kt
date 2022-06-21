package com.minal.admin.constant

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.system.Os.remove

@Suppress("UNCHECKED_CAST")
class Preferences constructor(private val mApplication: Application) {

    public val preferences: SharedPreferences
        get() = mApplication.getSharedPreferences(mApplication.packageName, Context.MODE_PRIVATE)

    fun save(key: String, value: Any?) {
        remove(key)
        if (value == null) return
        when (value) {
            is Boolean -> preferences.edit().putBoolean(key, value).apply()
            is Int -> preferences.edit().putInt(key, value).apply()
            is Float -> preferences.edit().putFloat(key, value).apply()
            is Long -> preferences.edit().putLong(key, value).apply()
            is String -> preferences.edit().putString(key, value).apply()
            is Enum<*> -> preferences.edit().putString(key, value.toString()).apply()
            else -> throw RuntimeException("Attempting to save non-primitive preference")
        }
    }

    fun getString(key: String) = preferences.getString(key, "") ?: ""

}