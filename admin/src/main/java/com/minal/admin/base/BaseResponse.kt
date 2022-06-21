package com.minal.admin.base

import com.squareup.moshi.Json


open class BaseResponse  {
    @Json(name = "message") var message: String ?= null
    @Json(name ="status") var status: Int ?= null

}