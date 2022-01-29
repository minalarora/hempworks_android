package com.hemp.works.login.data.model

data class User(
    var token: String? = null,
    var doctor: Doctor? = null,
    var admin: Admin? = null
)
