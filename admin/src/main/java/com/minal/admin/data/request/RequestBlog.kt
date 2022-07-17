package com.minal.admin.data.request


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class RequestBlog(
    var image: String?=null,
    var title: String?=null,
    var url: String?=null
)