package com.minal.admin.data.response.ledger

data class Address(
    var id:  Long? = null,
    var address1 : String?,
    var address2 : String?,
    var city     : String?,
    var state    : String?,
    var mobile   : String?,
    var pincode  : Int?,
    var doctor   : String? = null
)
