package com.minal.admin.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class ResponseAllDoctors(
    var profile     : String? = null,
    var status      : String,
    var credit      : Long,
    var cart        : Long,
    var name        : String,
    var clinic      : String,
    var mobile      : String,
    var email       : String,
    var certificate : String,
    var id          : String,
    var course      : String,
    var discount    : ArrayList<Discount>
)

data class Discount(
    var price      : Int,
    var percentage : Double
)
