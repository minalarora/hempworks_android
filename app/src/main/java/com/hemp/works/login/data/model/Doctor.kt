package com.hemp.works.login.data.model

data class Doctor(
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