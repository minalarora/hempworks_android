package com.minal.admin.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.minal.admin.data.remote.RestApi

class ViewModelFactory(private val apiHelper: RestApi) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminViewModel::class.java)) {
            return AdminViewModel() as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}