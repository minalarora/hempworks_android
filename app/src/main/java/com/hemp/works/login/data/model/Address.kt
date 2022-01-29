package com.hemp.works.login.data.model

data class Address(
    var id:  Int? = null,
    var address1 : String?,
    var address2 : String? = "",
    var city     : String?,
    var state    : String?,
    var pincode  : Int?,
    var doctor   : Doctor? = null
)
