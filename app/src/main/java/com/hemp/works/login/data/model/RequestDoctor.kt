package com.hemp.works.login.data.model

data class RequestDoctor(
    var name        : String?,
    var clinic      : String?,
    var mobile      : String?,
    var email       : String?,
    var password    : String?,
    var certificate : String?,
    var address     : Address?
)
