package com.minal.admin.utils

import com.google.gson.Gson
import com.minal.admin.base.BaseResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody

object ErrorUtils {

    private val mMoshi by lazy {
       Moshi.Builder().build()
    }

    fun getError(code: Int, errorBody: ResponseBody?): String {
        if(errorBody == null) return  "Server not reachable"
        if(code == 500) return  "Something went wrong !!"
        val jsonAdapter: JsonAdapter<BaseResponse> = mMoshi.adapter(BaseResponse::class.java)
        val baseResponse = jsonAdapter.fromJson(errorBody.charStream().readText())
        return baseResponse?.message ?: "Something went wrong !!"
    }


}