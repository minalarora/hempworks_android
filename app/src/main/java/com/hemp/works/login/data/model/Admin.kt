package com.hemp.works.login.data.model

data class Admin(
    var profile   : String?  = null,
    var active    : Boolean,
    var name      : String,
    var mobile    : String,
    var email     : String,
    var id        : String,
)
