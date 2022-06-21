package com.minal.admin.base

import com.google.gson.Gson
import com.minal.admin.data.remote.RestApi
import org.koin.core.KoinComponent
import org.koin.core.inject


/**
 * Created at 30/03/2020.
 */
open class BaseRepository : KoinComponent {

    val mRestApi by inject<RestApi>()
    private val mGson by lazy { Gson() }



}